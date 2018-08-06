package @packageName@.facade.controller;

import com.douyu.wsd.framework.common.domain.ResultVo;
import com.douyu.wsd.framework.common.lang.StringUtils;
import com.douyu.wsd.framework.common.math.NumberUtils;
import com.douyu.wsd.framework.oa.OaConstant;
import com.douyu.wsd.framework.oa.bean.OaUserInfoPo;
import com.douyu.wsd.framework.oa.config.OaConfig;
import com.douyu.wsd.framework.oa.utils.OaUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @Resource
    private OaConfig oaConfig;

    @GetMapping(value = "/api/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo logout(){

        return ResultVo.asRedirect(OaUtils.getOaLogoutUrl(oaConfig));
    }

    @GetMapping(value = "/api/getUserInfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultVo getUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        OaUserInfoPo oaUserInfoPo = (OaUserInfoPo)session.getAttribute(OaConstant.ADMIN_LOGIN_INFO);
        OAUser user = new OAUser();
        if(oaUserInfoPo != null) {
            user.setUserName(oaUserInfoPo.getUsername());
            user.setUserId(oaUserInfoPo.getUid());
        } else {
            user.setUserName(StringUtils.EMPTY);
            user.setUserId(NumberUtils.INTEGER_ZERO);
        }
        return ResultVo.asSuccess(user);
    }

    @Data
    public class OAUser {
        private String userName;
        private Integer userId;
    }
}
