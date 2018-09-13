package cn.exrick.manager.pojo;

import cn.exrick.common.enums.PaymentEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Transaction implements Serializable {
    private Long id;

    private String orderId;

    private String tid;

    private String tidNum;

    private BigDecimal total;

    private String currency;

    private PaymentEnum payment;

    private String paymentDes;

    private static final long serialVersionUID = 1L;
}