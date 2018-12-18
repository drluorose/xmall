package cn.exrick;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@EnableWebMvc
@EnableDubbo
@EnableDubboConfig
@SpringBootApplication
public class XmallServiceWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmallServiceWebApplication.class, args);
    }

}
