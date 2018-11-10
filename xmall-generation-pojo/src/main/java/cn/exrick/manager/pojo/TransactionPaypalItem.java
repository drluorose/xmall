package cn.exrick.manager.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransactionPaypalItem implements Serializable {
    private Long id;

    private String tPaypalId;

    private String name;

    private String description;

    private Integer quantity;

    private BigDecimal price;

    private BigDecimal tax;

    private String sku;

    private String currency;

    private static final long serialVersionUID = 1L;
}