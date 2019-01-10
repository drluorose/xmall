package cn.exrick.manager.web.controller.itemcat.req;

import cn.exrick.common.enums.EnableStatusEnum;
import cn.exrick.manager.web.controller.req.IdReq;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class ItemListReq extends IdReq {

    private EnableStatusEnum status;
}
