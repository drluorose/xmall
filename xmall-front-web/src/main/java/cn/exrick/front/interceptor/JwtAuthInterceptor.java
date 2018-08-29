package cn.exrick.front.interceptor;

import cn.exrick.common.annotation.JwtAuth;
import cn.exrick.common.exception.RequiredJwtAuthException;
import cn.exrick.common.exception.XmallException;
import cn.exrick.common.jwt.JwtAlgorithmEnum;
import cn.exrick.common.jwt.JwtKeyInfo;
import cn.exrick.sso.service.LoginService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Component
public class JwtAuthInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isAnnotationWithJwtAuth(handler)) {
            return true;
        }
        String signStr = request.getParameter("sign");
        String mid = request.getParameter("mid");
        if (StringUtils.isBlank(signStr) || StringUtils.isBlank(mid)) {
            throw new IllegalArgumentException("sign and mid is blank");
        }

        JwtKeyInfo jwtKeyInfo = loginService.getJwtKeyInfo(mid);
        if (Objects.isNull(jwtKeyInfo)) {
            throw new RequiredJwtAuthException("mid=" + mid);
        }

        Algorithm algorithm = JwtAlgorithmEnum.ECDSA256.getAlgorithm(jwtKeyInfo);
        if (Objects.isNull(algorithm)) {
            throw new XmallException("创建验证算法异常");
        }
        try {
            JWT.require(algorithm).build().verify(signStr);
        } catch (JWTVerificationException e) {
            throw new RequiredJwtAuthException("验证异常", e);
        }

        return true;
    }

    public boolean isAnnotationWithJwtAuth(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        JwtAuth jwtAuth = handlerMethod.getMethod().getAnnotation(JwtAuth.class);
        if (Objects.isNull(jwtAuth)) {
            return false;
        }
        return true;
    }
}
