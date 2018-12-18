package cn.exrick.manager.dto.front.req;

import cn.exrick.common.enums.PaymentEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PayOrderReq {

    @NotBlank
    private String orderId;

    @NotNull
    private PaymentEnum paymentEnum;
}
