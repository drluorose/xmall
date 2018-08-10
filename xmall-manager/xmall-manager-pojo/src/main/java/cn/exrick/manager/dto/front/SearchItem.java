package cn.exrick.manager.dto.front;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Exrickx
 */
@Data
public class SearchItem implements Serializable {

    private Long productId;

    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private String productImageBig;

    private String categoryName;

    private Integer cid;

}
