package cn.exrick.manager.web.controller.panel.res;

import cn.exrick.manager.pojo.TbPanelContent;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PanelContentDto {

    private int id;

    private int type;

    private long productId;

    private long sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    public PanelContentDto() {
    }

    public PanelContentDto(TbPanelContent tbPanelContent) {
        this.id = tbPanelContent.getId();
        this.type = tbPanelContent.getType();
        this.productId = tbPanelContent.getProductId();
        this.sortOrder = tbPanelContent.getSortOrder();
        this.fullUrl = tbPanelContent.getFullUrl();
        this.picUrl = tbPanelContent.getPicUrl();
        this.picUrl2 = tbPanelContent.getPicUrl2();
        this.picUrl3 = tbPanelContent.getPicUrl3();
    }
}
