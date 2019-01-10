package cn.exrick.manager.web.controller.order.req;

import cn.exrick.common.enums.OrderStatusEnum;
import cn.exrick.manager.web.controller.req.PageReq;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OrderSearchReq extends PageReq {
    private String orderId;

    private String mid;

    private String orderStartTime;

    private String orderEndTime;

    private OrderStatusEnum status;
}
