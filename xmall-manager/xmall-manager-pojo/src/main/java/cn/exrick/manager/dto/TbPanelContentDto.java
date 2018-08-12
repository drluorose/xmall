package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbPanelContent;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class TbPanelContentDto extends TbPanelContent {

    public TbPanelContentDto() {

    }

    public TbPanelContentDto(TbPanelContent tbPanelContent) {
        super();
        this.setId(tbPanelContent.getId());
        this.setPanelId(tbPanelContent.getPanelId());
        this.setType(tbPanelContent.getType());
        this.setProductId(tbPanelContent.getProductId());
        this.setSortOrder(tbPanelContent.getSortOrder());
        this.setFullUrl(tbPanelContent.getFullUrl());
        this.setPicUrl(tbPanelContent.getPicUrl());
        this.setPicUrl2(tbPanelContent.getPicUrl2());
        this.setPicUrl3(tbPanelContent.getPicUrl3());
        this.setCreated(tbPanelContent.getCreated());
        this.setUpdated(tbPanelContent.getUpdated());
    }

    /**
     * 关联商品信息
     */
    private BigDecimal salePrice;

    private String productName;

    private String subTitle;

    private String productImageBig;
}
