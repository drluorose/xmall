package cn.exrick.manager.dto;

import cn.exrick.manager.pojo.TbPanel;
import lombok.Data;

import java.util.List;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class TbPanelDto extends TbPanel {

    public TbPanelDto() {

    }

    public TbPanelDto(TbPanel tbPanel) {
        this.setId(tbPanel.getId());
        this.setName(tbPanel.getName());
        this.setType(tbPanel.getType());
        this.setSortOrder(tbPanel.getSortOrder());
        this.setPosition(tbPanel.getPosition());
        this.setLimitNum(tbPanel.getLimitNum());
        this.setStatus(tbPanel.getStatus());
        this.setRemark(tbPanel.getRemark());
        this.setCreated(tbPanel.getCreated());
        this.setUpdated(tbPanel.getUpdated());
    }

    private List<TbPanelContentDto> panelContents;
}
