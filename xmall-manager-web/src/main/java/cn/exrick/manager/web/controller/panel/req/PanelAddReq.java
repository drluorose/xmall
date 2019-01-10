package cn.exrick.manager.web.controller.panel.req;

import cn.exrick.common.enums.EnableStatusEnum;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class PanelAddReq {

    private Integer limitNum = 10;

    private Integer type;

    private String name;

    private Integer position = 0;

    private Integer sortOrder = 10;

    private EnableStatusEnum status = EnableStatusEnum.DISABLED;
}
