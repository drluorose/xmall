package cn.exrick.manager.service.req;

import cn.exrick.common.query.PageQuery;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class ItemSearchQuery extends PageQuery {

    private Integer category;

    private Long id;

    private String sku;

    private String name;
}
