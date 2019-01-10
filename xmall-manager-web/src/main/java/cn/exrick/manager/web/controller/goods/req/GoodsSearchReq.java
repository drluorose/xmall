package cn.exrick.manager.web.controller.goods.req;

import cn.exrick.manager.web.controller.req.PageReq;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class GoodsSearchReq extends PageReq {
    private Integer topCategory;

    private Integer secondaryCategory;

    private Integer thirdCategory;

    private String name;

    private String sku;

}
