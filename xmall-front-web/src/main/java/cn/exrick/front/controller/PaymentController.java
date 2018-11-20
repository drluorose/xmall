package cn.exrick.front.controller;

import cn.exrick.common.annotation.CustomReq;
import cn.exrick.common.annotation.JwtAuth;
import cn.exrick.common.annotation.SessionAuth;
import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.front.Member;
import cn.exrick.manager.dto.front.OrderPayAction;
import cn.exrick.manager.dto.front.req.PayOrderReq;
import cn.exrick.manager.service.ManagerOrderService;
import cn.exrick.manager.service.PaymentService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Reference
    private ManagerOrderService orderService;

    @Reference
    private PaymentService paymentService;

    @Autowired
    private MessageSource messageSource;

    @JwtAuth
    @SessionAuth
    @RequestMapping("/order")
    public Result<OrderPayAction> payOrder(@RequestBody PayOrderReq payOrderReq, @CustomReq Member user) {
        boolean authOrder = orderService.authOrder(user.getMid(), user.getMid());
        if (!authOrder) {
            Locale locale = LocaleContextHolder.getLocale();
            String paymentErrorOrderId = messageSource.getMessage("payment.error.orderId", null, locale);
            return new ResultUtil<OrderPayAction>().setErrorMsg(paymentErrorOrderId);
        }

        return null;
    }

    @JwtAuth
    @SessionAuth
    @RequestMapping("/paypal/cancel")
    public Result paypalCancel(HttpServletRequest request) {
        return null;
    }

    @JwtAuth
    @SessionAuth
    @RequestMapping("/paypal/ok")
    public Result paypalOk(HttpServletRequest request) {
        return null;
    }
}
