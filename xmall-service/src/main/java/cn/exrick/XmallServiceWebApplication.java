package cn.exrick;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@EnableWebMvc
@EnableDubboConfiguration
@SpringBootApplication
public class XmallServiceWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmallServiceWebApplication.class, args);
    }

}