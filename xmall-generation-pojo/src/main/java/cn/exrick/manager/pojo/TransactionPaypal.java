package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionPaypal implements Serializable {
    private Long id;

    private String tid;

    private String tPaypalId;

    private BigDecimal total;

    private String currency;

    private BigDecimal subtotal;

    private BigDecimal tax;

    private BigDecimal shipping;

    private BigDecimal handlingFee;

    private BigDecimal shippingDiscount;

    private BigDecimal insurance;

    private String selfUrl;

    private String approvalUrl;

    private String executeUrl;

    private static final long serialVersionUID = 1L;
}