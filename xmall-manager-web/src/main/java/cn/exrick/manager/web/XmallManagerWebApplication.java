package cn.exrick.manager.web;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author dongjiejie
 */
@EnableDubbo
@EnableDubboConfig
@SpringBootApplication
@EnableAspectJAutoProxy
public class XmallManagerWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(XmallManagerWebApplication.class, args);
    }

}
