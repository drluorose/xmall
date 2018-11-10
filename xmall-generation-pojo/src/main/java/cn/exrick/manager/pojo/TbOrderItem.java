package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TbOrderItem implements Serializable {
    private String id;

    private String sku;

    private String sellPoint;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private BigDecimal price;

    private BigDecimal totalFee;

    private String picPath;

    private static final long serialVersionUID = 1L;
}