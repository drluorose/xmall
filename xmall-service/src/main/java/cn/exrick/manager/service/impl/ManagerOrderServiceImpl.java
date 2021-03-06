package cn.exrick.manager.service.impl;

import cn.exrick.common.enums.OrderStatusEnum;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jedis.JedisClient;
import cn.exrick.common.pojo.PageResult;
import cn.exrick.common.query.OrderSearchParam;
import cn.exrick.manager.dto.OrderDetail;
import cn.exrick.manager.mapper.TbOrderItemMapper;
import cn.exrick.manager.mapper.TbOrderMapper;
import cn.exrick.manager.mapper.TbOrderShippingMapper;
import cn.exrick.manager.mapper.TbThanksMapper;
import cn.exrick.manager.mapper.ext.TbOrderExtMapper;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbOrderExample;
import cn.exrick.manager.pojo.TbOrderItem;
import cn.exrick.manager.pojo.TbOrderItemExample;
import cn.exrick.manager.pojo.TbOrderShipping;
import cn.exrick.manager.pojo.TbThanks;
import cn.exrick.manager.service.ManagerOrderService;
import cn.exrick.manager.service.req.OrderSearchQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Exrickx
 */
@Slf4j
@Component
@Service(interfaceClass = ManagerOrderService.class)
public class ManagerOrderServiceImpl implements ManagerOrderService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderExtMapper tbOrderExtMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    private TbThanksMapper tbThanksMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public PageResult<TbOrder> getOrderList(OrderSearchQuery orderSearchQuery) {
        OrderSearchParam orderSearchParam = new OrderSearchParam();
        orderSearchParam.setMid(orderSearchQuery.getMid());
        orderSearchParam.setOrderStartTime(orderSearchQuery.getOrderStartTime());
        orderSearchParam.setOrderEndTime(orderSearchQuery.getOrderEndTime());
        orderSearchParam.setStatus(orderSearchQuery.getStatus());
        //分页
        PageHelper.startPage(orderSearchQuery.getPageNo(), orderSearchQuery.getPageSize());
        List<TbOrder> list = tbOrderExtMapper.selectByMulti(orderSearchParam);
        PageInfo<TbOrder> pageInfo = new PageInfo<>(list);
        PageResult<TbOrder> result = new PageResult<>();
        result.setData(list);
        result.setPageNo(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public Long countOrder() {

        TbOrderExample example = new TbOrderExample();
        Long result = tbOrderMapper.countByExample(example);
        if (result == null) {
            throw new XmallException("统计订单数目失败");
        }
        return result;
    }

    @Override
    public OrderDetail getOrderDetail(String orderId) {

        OrderDetail orderDetail = new OrderDetail();
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<TbOrderItem> orderItemList = tbOrderItemMapper.selectByExample(example);

        TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(orderId);

        orderDetail.setTbOrder(tbOrder);
        orderDetail.setTbOrderItem(orderItemList);
        orderDetail.setTbOrderShipping(tbOrderShipping);
        return orderDetail;
    }

    @Override
    public boolean authOrder(String mid, String orderId) {
        TbOrderExample tbOrderExample = new TbOrderExample();
        tbOrderExample.createCriteria().andOrderIdEqualTo(orderId)
            .andMidEqualTo(mid)
            .andStatusEqualTo(OrderStatusEnum.UNPAID);
        List<TbOrder> tbOrders = this.tbOrderMapper.selectByExample(tbOrderExample);
        if (CollectionUtils.isEmpty(tbOrders)) {
            return false;
        }
        return true;
    }

    @Override
    public int deliver(String orderId, String shippingName, String shippingCode, BigDecimal postFee) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setShippingName(shippingName);
        o.setShippingCode(shippingCode);
        o.setPostFee(postFee);
        o.setConsignTime(new Date());
        o.setUpdateTime(new Date());
        //之前忘记设置常量了 将就这样看吧 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
        o.setStatus(OrderStatusEnum.SHIPPED);
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }

    @Override
    public int remark(String orderId, String message) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setBuyerMessage(message);
        o.setUpdateTime(new Date());
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }

    @Override
    public int cancelOrderByAdmin(String orderId) {

        TbOrder o = tbOrderMapper.selectByPrimaryKey(orderId);
        o.setCloseTime(new Date());
        o.setUpdateTime(new Date());
        //之前忘记设置常量了 将就这样看吧 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
        o.setStatus(OrderStatusEnum.CLOSED);
        tbOrderMapper.updateByPrimaryKey(o);
        return 1;
    }

    @Override
    public int deleteOrder(String id) {

        if (tbOrderMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除订单数失败");
        }

        TbOrderItemExample example = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = example.createCriteria();
        criteria.andOrderIdEqualTo(id);
        List<TbOrderItem> list = tbOrderItemMapper.selectByExample(example);
        for (TbOrderItem tbOrderItem : list) {
            if (tbOrderItemMapper.deleteByPrimaryKey(tbOrderItem.getId()) != 1) {
                throw new XmallException("删除订单商品失败");
            }
        }

        if (tbOrderShippingMapper.deleteByPrimaryKey(id) != 1) {
            throw new XmallException("删除物流失败");
        }
        return 1;
    }

    @Override
    public int cancelOrder() {

        TbOrderExample example = new TbOrderExample();
        List<TbOrder> list = tbOrderMapper.selectByExample(example);
        for (TbOrder tbOrder : list) {
            judgeOrder(tbOrder);
        }
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
                    throw new XmallException("设置订单关闭失败");
                }
            } else {
                //返回到期时间
                long time = tbOrder.getCreateTime().getTime() + 1000 * 60 * 60 * 24;
                result = String.valueOf(time);
            }
        }
        return result;
    }

    @Override
    public int passPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(1);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStatusEnum.SUCCESS);
            tbOrder.setEndTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int backPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(2);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStatusEnum.REFUNDED);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int notShowPay(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //展示捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        tbThanks.setState(3);
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStatusEnum.SUCCESS);
            tbOrder.setEndTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int editPay(String tokenName, String token, TbThanks tbThanks) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(tbThanks.getId().toString())) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        if (tbThanksMapper.updateByPrimaryKey(tbThanks) != 1) {
            return 0;
        }
        return 1;
    }

    @Override
    public int payDelNotNotify(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //获得捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        //删除捐赠
        if (tbThanksMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStatusEnum.REFUNDED);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int payDel(String tokenName, String token, String id) {

        //验证token
        if (StringUtils.isBlank(tokenName) || StringUtils.isBlank(tokenName) || StringUtils.isBlank(id)) {
            return -1;
        }
        String value = jedisClient.get(tokenName);
        if (!value.equals(token)) {
            return -1;
        }
        //获得捐赠
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Integer.valueOf(id));
        if (tbThanks == null) {
            return 0;
        }
        //删除捐赠
        if (tbThanksMapper.deleteByPrimaryKey(Integer.valueOf(id)) != 1) {
            return 0;
        }
        //修改订单状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if (tbOrder != null) {
            tbOrder.setStatus(OrderStatusEnum.REFUNDED);
            tbOrder.setCloseTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if (tbOrderMapper.updateByPrimaryKey(tbOrder) != 1) {
                return 0;
            }
        }
        return 1;
    }

}
