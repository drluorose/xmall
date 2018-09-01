package cn.exrick.front.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Exrickx
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private LimitRaterInterceptor limitRaterInterceptor;

    @Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Autowired
    private SessionAuthInterceptor sessionAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
//        registry.addInterceptor(limitRaterInterceptor).addPathPatterns("/**");
        registry.addInterceptor(jwtAuthInterceptor).addPathPatterns("/**");
        registry.addInterceptor(sessionAuthInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "PUT", "POST", "OPTIONS")
            .allowCredentials(true).maxAge(3600);
    }
}
