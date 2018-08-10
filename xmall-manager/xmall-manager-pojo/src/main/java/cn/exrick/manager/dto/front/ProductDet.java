package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Exrickx
 */
@Data
public class ProductDet implements Serializable {

    private Long productId;

    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private Long limitNum;

    private String productImageBig;

    private String detail;

    private List<?> productImageSmall;

}
