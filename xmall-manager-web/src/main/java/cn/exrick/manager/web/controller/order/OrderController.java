package cn.exrick.manager.web.controller.order;

import cn.exrick.common.pojo.PageResult;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.OrderDetail;
import cn.exrick.manager.pojo.TbOrder;
import cn.exrick.manager.service.ManagerOrderService;
import cn.exrick.manager.service.req.OrderSearchQuery;
import cn.exrick.manager.web.controller.order.req.OrderSearchReq;
import com.alibaba.dubbo.config.annotation.Reference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author Exrickx
 */
@RestController
@Api(description = "订单管理")
public class OrderController {

    @Reference
    private ManagerOrderService orderService;

    @RequestMapping(value = "/order/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取订单列表")
    public Result<PageResult> getOrderList(@RequestBody OrderSearchReq orderSearchReq) {

        //获取客户端需要排序的列
        OrderSearchQuery orderSearchQuery = new OrderSearchQuery();
        orderSearchQuery.setMid(orderSearchReq.getMid());
        orderSearchQuery.setOrderEndTime(orderSearchReq.getOrderEndTime());
        orderSearchQuery.setOrderStartTime(orderSearchReq.getOrderStartTime());
        orderSearchQuery.setPageNo(orderSearchReq.getPageNo());
        orderSearchQuery.setPageSize(orderSearchReq.getPageSize());
        orderSearchQuery.setStatus(orderSearchReq.getStatus());

        PageResult<TbOrder> result = orderService.getOrderList(orderSearchQuery);
        return new ResultUtil<PageResult>().setData(result);
    }

    @RequestMapping(value = "/order/count", method = RequestMethod.GET)
    @ApiOperation(value = "获取订单总数")
    public Result<Object> getOrderCount() {

        Long result = orderService.countOrder();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/order/detail/{orderId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取订单详情")
    public Result<Object> getOrderDetail(@PathVariable String orderId) {

        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
        return new ResultUtil<Object>().setData(orderDetail);
    }

    @RequestMapping(value = "/order/remark", method = RequestMethod.POST)
    @ApiOperation(value = "订单备注")
    public Result<Object> remark(@RequestParam String orderId,
                                 @RequestParam(required = false) String message) {

        orderService.remark(orderId, message);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/deliver", method = RequestMethod.POST)
    @ApiOperation(value = "订单发货")
    public Result<Object> deliver(@RequestParam String orderId,
                                  @RequestParam String shippingName,
                                  @RequestParam String shippingCode,
                                  @RequestParam BigDecimal postFee) {

        orderService.deliver(orderId, shippingName, shippingCode, postFee);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/cancel/{orderId}", method = RequestMethod.GET)
    @ApiOperation(value = "订单取消")
    public Result<Object> cancelOrderByAdmin(@PathVariable String orderId) {

        orderService.cancelOrderByAdmin(orderId);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/del/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除订单")
    public Result<Object> getUserInfo(@PathVariable String[] ids) {

        for (String id : ids) {
            orderService.deleteOrder(id);
        }
        return new ResultUtil<Object>().setData(null);
    }
}
