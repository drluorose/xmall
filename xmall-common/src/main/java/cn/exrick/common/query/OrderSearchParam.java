package cn.exrick.common.query;

import cn.exrick.common.enums.OrderStatusEnum;
import lombok.Data;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
public class OrderSearchParam {
    private String orderId;

    private String mid;

    private String orderStartTime;

    private String orderEndTime;

    private OrderStatusEnum status;
}
