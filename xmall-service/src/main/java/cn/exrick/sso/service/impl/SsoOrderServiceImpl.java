package cn.exrick.sso.service.impl;

import cn.exrick.common.enums.OrderStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.common.utils.IDUtil;
import cn.exrick.manager.dto.DtoUtil;
import cn.exrick.manager.dto.front.CartProduct;
import cn.exrick.manager.dto.front.Order;
import cn.exrick.manager.dto.front.OrderInfo;
import cn.exrick.manager.dto.front.PageOrder;
import cn.exrick.manager.mapper.TbMemberMapper;
import cn.exrick.manager.mapper.TbOrderItemMapper;
import cn.exrick.manager.mapper.TbOrderMapper;
import cn.exrick.manager.mapper.TbOrderShippingMapper;
import cn.exrick.manager.mapper.TbThanksMapper;
import cn.exrick.manager.pojo.TbAddress;
import cn.exrick.manager.pojo.TbMember;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbOrderExample;
import cn.exrick.manager.pojo.TbOrderItem;
import cn.exrick.manager.pojo.TbOrderItemExample;
import cn.exrick.manager.pojo.TbOrderShipping;
import cn.exrick.manager.pojo.TbThanks;
import cn.exrick.sso.service.SsoOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = SsoOrderService.class)
public class SsoOrderServiceImpl implements SsoOrderService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;  //订单物流

    @Autowired
    private TbThanksMapper tbThanksMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${CART_PRE}")
    private String CART_PRE;

    @Value("${PAY_EXPIRE}")
    private int PAY_EXPIRE;

    @Override
    public PageOrder getOrderList(Long userId, int page, int size) {

        //分页
        if (page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page, size);

        PageOrder pageOrder = new PageOrder();
        List<Order> list = new ArrayList<>();

        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        example.setOrderByClause("create_time DESC");
        List<TbOrder> listOrder = tbOrderMapper.selectByExample(example);
        for (TbOrder tbOrder : listOrder) {

            judgeOrder(tbOrder);

            Order order = new Order();
            //orderId
            order.setOrderId(Long.valueOf(tbOrder.getOrderId()));
            //orderStatus
            order.setOrderStatus(String.valueOf(tbOrder.getStatus()));
            //createDate
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = formatter.format(tbOrder.getCreateTime());
            order.setCreateDate(date);
            //address
            TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(tbOrder.getOrderId());
            TbAddress address = new TbAddress();
            address.setUserName(tbOrderShipping.getReceiverName());
//            address.setStreetName(tbOrderShipping.getReceiverAddress());
            address.setTel(tbOrderShipping.getReceiverPhone());
            order.setAddressInfo(address);
            //orderTotal
            if (tbOrder.getPayment() == null) {
                order.setOrderTotal(new BigDecimal(0));
            } else {
                order.setOrderTotal(tbOrder.getPayment());
            }
            //goodsList
            TbOrderItemExample exampleItem = new TbOrderItemExample();
            TbOrderItemExample.Criteria criteriaItem = exampleItem.createCriteria();
            criteriaItem.andOrderIdEqualTo(tbOrder.getOrderId());
            List<TbOrderItem> listItem = tbOrderItemMapper.selectByExample(exampleItem);
            List<CartProduct> listProduct = new ArrayList<>();
            for (TbOrderItem tbOrderItem : listItem) {

                CartProduct cartProduct = DtoUtil.TbOrderItem2CartProduct(tbOrderItem);

                listProduct.add(cartProduct);
            }
            order.setGoodsList(listProduct);
            list.add(order);
        }
        PageInfo<Order> pageInfo = new PageInfo<>(list);
        pageOrder.setTotal(getMemberOrderCount(userId));
        pageOrder.setData(list);
        return pageOrder;
    }

    @Override
    public Order getOrder(Long orderId) {

        Order order = new Order();

        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(String.valueOf(orderId));
        if (tbOrder == null) {
            throw new XmallException("通过id获取订单失败");
        }

        String validTime = judgeOrder(tbOrder);
        if (validTime != null) {
            order.setFinishDate(validTime);
        }

        //orderId
        order.setOrderId(Long.valueOf(tbOrder.getOrderId()));
        //orderStatus
        order.setOrderStatus(String.valueOf(tbOrder.getStatus()));
        //createDate
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String createDate = formatter.format(tbOrder.getCreateTime());
        order.setCreateDate(createDate);
        //payDate
        if (tbOrder.getPaymentTime() != null) {
            String payDate = formatter.format(tbOrder.getPaymentTime());
            order.setPayDate(payDate);
        }
        //closeDate
        if (tbOrder.getCloseTime() != null) {
            String closeDate = formatter.format(tbOrder.getCloseTime());
            order.setCloseDate(closeDate);
        }
        //finishDate
        if (tbOrder.getEndTime() != null && tbOrder.getStatus() == OrderStatusEnum.SUCCESS) {
            String finishDate = formatter.format(tbOrder.getEndTime());
            order.setFinishDate(finishDate);
        }
        //address
        TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(tbOrder.getOrderId());
        TbAddress address = new TbAddress();
        address.setUserName(tbOrderShipping.getReceiverName());
//        address.setStreetName(tbOrderShipping.getReceiverAddress());
        address.setTel(tbOrderShipping.getReceiverPhone());
        order.setAddressInfo(address);
        //orderTotal
        if (tbOrder.getPayment() == null) {
            order.setOrderTotal(new BigDecimal(0));
        } else {
            order.setOrderTotal(tbOrder.getPayment());
        }
        //goodsList
        TbOrderItemExample exampleItem = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteriaItem = exampleItem.createCriteria();
        criteriaItem.andOrderIdEqualTo(tbOrder.getOrderId());
        List<TbOrderItem> listItem = tbOrderItemMapper.selectByExample(exampleItem);
        List<CartProduct> listProduct = new ArrayList<>();
        for (TbOrderItem tbOrderItem : listItem) {

            CartProduct cartProduct = DtoUtil.TbOrderItem2CartProduct(tbOrderItem);

            listProduct.add(cartProduct);
        }
        order.setGoodsList(listProduct);
        return order;
    }

    @Override
    public int cancelOrder(Long orderId) {

        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(String.valueOf(orderId));
        if (tbOrder == null) {
            throw new XmallException("通过id获取订单失败");
        }
        tbOrder.setStatus(OrderStatusEnum.CLOSED);
        tbOrder.setCloseTime(new Date());
        if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
            throw new XmallException("取消订单失败");
        }
        return 1;
    }

    @Override
    public Long createOrder(OrderInfo orderInfo) {

        TbMember member = tbMemberMapper.selectByPrimaryKey(Long.valueOf(orderInfo.getUserId()));
        if (member == null) {
            throw new XmallException("获取下单用户失败");
        }

        TbOrder order = new TbOrder();
        //生成订单ID
        Long orderId = IDUtil.getRandomId();
        order.setOrderId(String.valueOf(orderId));
        order.setUserId(Long.valueOf(orderInfo.getUserId()));
        order.setBuyerNick(member.getUsername());
        order.setPayment(orderInfo.getOrderTotal());
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        //0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭，6、交易失败
        order.setStatus(OrderStatusEnum.UNPAID);

        if (tbOrderMapper.insert(order) != 1) {
            throw new XmallException("生成订单失败");
        }

        List<CartProduct> list = orderInfo.getGoodsList();
        for (CartProduct cartProduct : list) {
            TbOrderItem orderItem = new TbOrderItem();
            //生成订单商品ID
            Long orderItemId = IDUtil.getRandomId();
            orderItem.setId(String.valueOf(orderItemId));
            orderItem.setItemId(String.valueOf(cartProduct.getProductId()));
            orderItem.setOrderId(String.valueOf(orderId));
            orderItem.setNum(Math.toIntExact(cartProduct.getProductNum()));
            orderItem.setPrice(cartProduct.getSalePrice());
            orderItem.setTitle(cartProduct.getProductName());
            orderItem.setPicPath(cartProduct.getProductImg());
            orderItem.setTotalFee(cartProduct.getSalePrice().multiply(BigDecimal.valueOf(cartProduct.getProductNum())));

            if (tbOrderItemMapper.insert(orderItem) != 1) {
                throw new XmallException("生成订单商品失败");
            }

            //删除购物车中含该订单的商品
            try {
                List<String> jsonList = jedisClient.hvals(CART_PRE + ":" + orderInfo.getUserId());
                for (String json : jsonList) {
                    CartProduct cart = new Gson().fromJson(json, CartProduct.class);
                    if (cart.getProductId().equals(cartProduct.getProductId())) {
                        jedisClient.hdel(CART_PRE + ":" + orderInfo.getUserId(), cart.getProductId() + "");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //物流表
        TbOrderShipping orderShipping = new TbOrderShipping();
        orderShipping.setOrderId(String.valueOf(orderId));
        orderShipping.setReceiverName(orderInfo.getUserName());
        orderShipping.setReceiverAddress(orderInfo.getStreetName());
        orderShipping.setReceiverPhone(orderInfo.getTel());
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        if (tbOrderShippingMapper.insert(orderShipping) != 1) {
            throw new XmallException("生成物流信息失败");
        }

        return orderId;
    }

    @Override
    public int delOrder(Long orderId) {

        if (tbOrderMapper.deleteByPrimaryKey(String.valueOf(orderId)) != 1) {
            throw new XmallException("删除订单失败");
        }

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(String.valueOf(orderId));
        List<TbOrderItem> list = tbOrderItemMapper.selectByExample(example);
        for (TbOrderItem tbOrderItem : list) {
            if (tbOrderItemMapper.deleteByPrimaryKey(tbOrderItem.getId()) != 1) {
                throw new XmallException("删除订单商品失败");
            }
        }

        if (tbOrderShippingMapper.deleteByPrimaryKey(String.valueOf(orderId)) != 1) {
            throw new XmallException("删除物流失败");
        }
        return 1;
    }

    @Override
    public int payOrder(TbThanks tbThanks) {

        tbThanks.setDate(new Date());
//        TbMember tbMember=tbMemberMapper.selectByPrimaryKey(Long.valueOf(tbThanks.getUserId()));
//        if(tbMember!=null){
//            tbThanks.setUsername(tbMember.getUsername());
//        }
//        if(tbThanksMapper.insert(tbThanks)!=1){
//            throw new XmallException("保存捐赠支付数据失败");
//        }

        //设置订单为已付款
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        tbOrder.setStatus(OrderStatusEnum.PAID);
        tbOrder.setUpdateTime(new Date());
        tbOrder.setPaymentTime(new Date());
        if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
            throw new XmallException("更新订单失败");
        }
        //发送通知确认邮件
        String tokenName = UUID.randomUUID().toString();
        String token = UUID.randomUUID().toString();
        //设置验证token键值对 tokenName:token
        jedisClient.set(tokenName, token);
        jedisClient.expire(tokenName, PAY_EXPIRE);
        return 1;
    }

    /**
     * 判断订单是否超时未支付
     */
    public String judgeOrder(TbOrder tbOrder) {

        String result = null;
        if (tbOrder.getStatus() == OrderStatusEnum.UNPAID) {
            //判断是否已超1天
            long diff = System.currentTimeMillis() - tbOrder.getCreateTime().getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days >= 1) {
                //设置失效
                tbOrder.setStatus(OrderStatusEnum.CLOSED);
                tbOrder.setCloseTime(new Date());
                if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                    throw new XmallException("更新订单失效失败");
                }
            } else {
                //返回到期时间
                long time = tbOrder.getCreateTime().getTime() + 1000 * 60 * 60 * 24;
                result = String.valueOf(time);
            }
        }
        return result;
    }

    public int getMemberOrderCount(Long userId) {

        TbOrderExample example = new TbOrderExample();
        TbOrderExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<TbOrder> listOrder = tbOrderMapper.selectByExample(example);
        if (listOrder != null) {
            return listOrder.size();
        }
        return 0;
    }
}
