package cn.exrick.manager.web.controller.goods.req;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddGoodsReq {

    private String title;

    private String sellPoint;

    private BigDecimal price;

    private String image;

    private Long cid;

    private String itemDesc;
}
