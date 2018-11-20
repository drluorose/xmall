package cn.exrick.manager.service.impl;

import cn.exrick.common.utils.ObjectId;
import cn.exrick.manager.component.PayPalComponent;
import cn.exrick.manager.dto.front.OrderPayAction;
import cn.exrick.manager.dto.front.req.PayOrderReq;
import cn.exrick.manager.mapper.TbOrderItemMapper;
import cn.exrick.manager.mapper.TbOrderMapper;
import cn.exrick.manager.mapper.TbOrderShippingMapper;
import cn.exrick.manager.mapper.TransactionMapper;
import cn.exrick.manager.pojo.TbItem;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.pojo.TbOrderItem;
import cn.exrick.manager.pojo.TbOrderItemExample;
import cn.exrick.manager.pojo.TbOrderShipping;
import cn.exrick.manager.pojo.TbOrderShippingExample;
import cn.exrick.manager.pojo.Transaction;
import cn.exrick.manager.service.PaymentService;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Slf4j
@Component
@Service(interfaceClass = PaymentService.class)
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private PayPalComponent payPalComponent;

    @Override
    public OrderPayAction payment(PayOrderReq payOrderReq) throws IOException {
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(payOrderReq.getOrderId());
        if (Objects.isNull(tbOrder)) {
            throw new IllegalArgumentException("order id is error:" + payOrderReq.getOrderId());
        }
        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        tbOrderItemExample.createCriteria().andOrderIdEqualTo(payOrderReq.getOrderId());
        List<TbOrderItem> tbItems = tbOrderItemMapper.selectByExample(tbOrderItemExample);
        TbOrderShipping tbOrderShipping = getOrderShipping(payOrderReq.getOrderId());
        Transaction transaction = createTransaction(tbOrder, payOrderReq);
        OrderPayAction orderPayAction = this.payPalComponent.pay(tbOrder, tbItems, tbOrderShipping, transaction);
        return orderPayAction;
    }

    private Transaction createTransaction(TbOrder tbOrder, PayOrderReq payOrderReq) {
        Transaction transaction = new Transaction();
        transaction.setCurrency("USA");
        transaction.setOrderId(tbOrder.getOrderId());
        transaction.setPayment(payOrderReq.getPaymentEnum());
        transaction.setPaymentDes(payOrderReq.getPaymentEnum().getCode());
        ObjectId objectId = ObjectId.get();
        transaction.setTid(objectId.toHexString());
        transaction.setTidNum(objectId.toDigitString());
        transaction.setTotal(tbOrder.getPayment());
        this.transactionMapper.insert(transaction);
        return transaction;
    }

    private TbOrderShipping getOrderShipping(String orderId) {
        TbOrderShippingExample example = new TbOrderShippingExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<TbOrderShipping> tbOrderShippings = tbOrderShippingMapper.selectByExample(example);
        return tbOrderShippings.get(0);
    }

    private void payByPaypal(TbOrder tbOrder, List<TbItem> tbItems, TbOrderShipping tbOrderShipping) {

    }
}
