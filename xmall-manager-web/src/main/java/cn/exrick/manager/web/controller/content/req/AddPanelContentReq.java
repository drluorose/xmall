package cn.exrick.manager.web.controller.content.req;

import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class AddPanelContentReq {

    private Integer panelId;

    private Integer type;

    private Long productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;
}
