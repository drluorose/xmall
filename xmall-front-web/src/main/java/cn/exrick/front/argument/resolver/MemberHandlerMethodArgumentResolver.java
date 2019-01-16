package cn.exrick.front.argument.resolver;

import cn.exrick.common.annotation.JwtAuth;
import cn.exrick.common.pojo.MemberInfoDto;
import cn.exrick.front.annotation.MemberInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
public class MemberHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        MemberInfo memberInfo = methodParameter.getAnnotatedElement().getAnnotation(MemberInfo.class);
        JwtAuth jwtAuth = methodParameter.getAnnotatedElement().getAnnotation(JwtAuth.class);
        if (Objects.isNull(memberInfo) || Objects.isNull(jwtAuth)) {
            return false;
        }
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        MemberInfoDto memberInfoDto = new MemberInfoDto();
        memberInfoDto.setMid(getMid(nativeWebRequest));
        return memberInfoDto;
    }

    private String getMid(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        return servletRequest.getParameter("mid");
    }

}
