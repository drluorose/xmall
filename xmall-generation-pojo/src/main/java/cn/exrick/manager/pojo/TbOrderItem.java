package cn.exrick.manager.pojo;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class TbOrderItem {
    private String id;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private BigDecimal price;

    private BigDecimal totalFee;

    private String picPath;
}