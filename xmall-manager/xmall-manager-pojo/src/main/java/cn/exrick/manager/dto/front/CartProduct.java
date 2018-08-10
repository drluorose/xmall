package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Exrickx
 */
@Data
public class CartProduct implements Serializable {

    private Long productId;

    private BigDecimal salePrice;

    private Long productNum;

    private Long limitNum;

    private String checked;

    private String productName;

    private String productImg;
}
