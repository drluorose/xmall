package cn.exrick.manager.web.controller.req;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class UpdateContentCategoryReq {

    private Integer id;

    private String name;

    private Integer limitNum;

    private Integer sortOrder;
}
