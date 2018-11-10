package cn.exrick.manager.service;

import cn.exrick.manager.dto.front.OrderPayAction;
import cn.exrick.manager.dto.front.req.PayOrderReq;

import java.io.IOException;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public interface PaymentService {

    /**
     * 支付订单
     *
     * @param payOrderReq
     *
     * @return
     */
    OrderPayAction payment(PayOrderReq payOrderReq) throws IOException;
}
