package cn.exrick.manager.web.controller.panel.res;

import cn.exrick.common.pojo.DataTablesResult;
import cn.exrick.manager.pojo.TbPanel;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PanelDto {
    private int id;

    private String name;

    private int type;

    private int sortOrder;

    private int position;

    private int limitNum;

    private int status;

    private String remark;

    public PanelDto() {
    }

    public PanelDto(TbPanel tbPanel) {
        this.id = tbPanel.getId();
        this.name = tbPanel.getName();
        this.type = tbPanel.getType();
        this.sortOrder = tbPanel.getSortOrder();
        this.position = tbPanel.getPosition();
        this.limitNum = tbPanel.getLimitNum();
        this.status = tbPanel.getStatus().getNumber();
        this.remark = tbPanel.getRemark();
    }

    private DataTablesResult dataTablesResult;
}
