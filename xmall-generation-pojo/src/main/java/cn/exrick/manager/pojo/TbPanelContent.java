package cn.exrick.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class TbPanelContent {
    private Integer id;

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    private Date created;

    private Date updated;


    /**
     * 关联商品信息
     */
    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private String productImageBig;
}