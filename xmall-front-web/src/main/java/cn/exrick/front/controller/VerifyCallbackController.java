package cn.exrick.front.controller;

import cn.exrick.sso.service.RegisterService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Controller
@RequestMapping("/reigster/verify/callback")
public class VerifyCallbackController {

    @Reference
    private RegisterService registerService;

    @RequestMapping(method = RequestMethod.GET)
    public String verifyCallback(@RequestParam("id") String id, @RequestParam("content") String content) {
        boolean callback = registerService.verifyCallback(id, content);
        if (!callback) {
            return "fail";
        }
        return "success";
    }
}
