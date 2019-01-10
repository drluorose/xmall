package cn.exrick.manager.web.controller.req;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PageReq extends IdReq {

    private Integer pageSize;

    private Integer pageNo;
}
