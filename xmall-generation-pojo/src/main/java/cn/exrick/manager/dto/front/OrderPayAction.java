package cn.exrick.manager.dto.front;

import cn.exrick.common.enums.OrderPayActionEnum;
import cn.exrick.common.enums.PaymentEnum;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OrderPayAction {
    /**
     * 支付后的动作
     */
    private OrderPayActionEnum action;

    /**
     * 支付类型
     */
    private PaymentEnum paymentEnum;

    /**
     * action的url
     */
    private String actionUrl;

    /**
     * 支付流水号
     */
    private String payId;

    /**
     * 支付的状态
     */
    private String state;
}
