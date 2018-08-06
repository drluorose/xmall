package @packageName@.facade.controller;

import com.douyu.wsd.framework.oa.OaConstant;
import com.douyu.wsd.framework.oa.bean.OaUserInfoPo;
import com.douyu.wsd.framework.oa.config.OaConfig;
import com.douyu.wsd.framework.oa.service.OaService;
import com.douyu.wsd.framework.oa.utils.OaUtils;

import java.io.UnsupportedEncodingException;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 登录控制器
 */
@Controller
public class LoginOAController {

    private static final Logger logger = LoggerFactory.getLogger(LoginOAController.class);

    @Resource
    private OaService oaService;

    @Resource
    private OaConfig oaConfig;

    private String oaUrl;

    @PostConstruct
    private void init() throws UnsupportedEncodingException {
        oaUrl = OaUtils.getOaLoginUrl(oaConfig);
    }

    @RequestMapping(value = "/login/callback")
    public String callback(@RequestParam(value = "code") String code, HttpSession httpSession, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        logger.info("callback|用户登录成功|code:{}", code);

        OaUserInfoPo oaUserInfoPo;

        try {
            //获取用户信息
            oaUserInfoPo = oaService.getUserInfoFromOA(code);
        } catch (Exception e) {
            response.sendRedirect(oaUrl);
            return null;
        }

        if (null != oaUserInfoPo) {
            httpSession.setAttribute(OaConstant.ADMIN_LOGIN_INFO, oaUserInfoPo);
            logger.info("oauth|用户登录成功|用户信息:{}", oaUserInfoPo);
            Long now = System.currentTimeMillis();
            httpSession.setAttribute(OaConstant.CSRF_TOKEN, now.toString());
            Cookie cookie = new Cookie(OaConstant.CSRF_TOKEN, now.toString());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(OaConstant.LONG_EXPIRED_TIME);
            cookie.setPath("/");
            response.addCookie(cookie);

            //https下redirect会变成http
            String redirectUrl = oaConfig.getRedirectUrl();
            String scheme = redirectUrl.substring(0,redirectUrl.indexOf("://"));

            Integer port = request.getServerPort();
            String homeUrl = scheme+"://"+ request.getServerName()+":"+port;

            if(port == 80 || port == 443) {
                homeUrl = scheme+"://"+ request.getServerName();
            }

            return "redirect:" + homeUrl;//返回主页
        } else {
            response.sendRedirect(oaUrl);
            return null;
        }

    }

    private String getHomePage(HttpServletRequest request) {
        //https下redirect会变成http
        String redirectUrl = oaConfig.getRedirectUrl();
        String scheme = redirectUrl.substring(0,redirectUrl.indexOf("://"));

        Integer port = request.getServerPort();
        String homeUrl = scheme+"://"+ request.getServerName()+":"+port;

        if(port == 80 || port == 443) {
            homeUrl = scheme+"://"+ request.getServerName();
        }
        return homeUrl;
    }

    /**
     * 退出登录接口
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout/callback")
    public String logoutCallback(HttpServletRequest request, HttpServletResponse response) {
        // 清除session
        request.getSession().removeAttribute(OaConstant.ADMIN_LOGIN_INFO);
        this.removeCookie(request,response);
        Object session = request.getSession().getAttribute(OaConstant.ADMIN_LOGIN_INFO);
        String homePage = getHomePage(request);
        logger.info("======================================登出回调,session == null ,result :{}, homePage:{} " , session == null, homePage);
        return "redirect:" + getHomePage(request);
    }

    private void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(OaConstant.CSRF_TOKEN)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }


}
