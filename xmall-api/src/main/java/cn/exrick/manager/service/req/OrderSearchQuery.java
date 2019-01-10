package cn.exrick.manager.service.req;

import cn.exrick.common.enums.OrderStatusEnum;
import cn.exrick.common.query.PageQuery;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OrderSearchQuery extends PageQuery {

    private String orderId;

    private String mid;

    private String orderStartTime;

    private String orderEndTime;

    private OrderStatusEnum status;
}
