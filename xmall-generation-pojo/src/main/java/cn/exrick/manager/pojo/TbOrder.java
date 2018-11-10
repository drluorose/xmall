package cn.exrick.manager.pojo;

import cn.exrick.common.enums.OrderStatusEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbOrder implements Serializable {
    private String orderId;

    private String mid;

    private BigDecimal payment;

    private Integer paymentType;

    private BigDecimal postFee;

    private OrderStatusEnum status;

    private Date createTime;

    private Date updateTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private Long userId;

    private String buyerMessage;

    private String buyerNick;

    private Boolean buyerComment;

    private static final long serialVersionUID = 1L;
}