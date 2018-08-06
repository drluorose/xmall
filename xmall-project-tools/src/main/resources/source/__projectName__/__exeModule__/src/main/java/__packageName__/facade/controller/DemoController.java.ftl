package @packageName@.facade.controller;

import @packageName@.vo.ListReq;
import @packageName@.vo.UserVo;
import @packageName@.vo.AddReq;

import com.douyu.wsd.framework.common.domain.StdResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/users")
    public StdResponse<UserVo> queryUsers(ListReq req) {
        UserVo user = new UserVo();
        user.setId(1L);
        user.setUsername("konglz");
        user.setAge(88);
        return StdResponse.newOk();
    }

    @PostMapping("/users")
    public StdResponse addUsers(@Validated AddReq req) {
        //执行业务逻辑
        return StdResponse.newOk();
    }

}