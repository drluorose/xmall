package cn.exrick.front;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author dongjiejie
 */
@EnableWebMvc
@EnableDubboConfiguration
@SpringBootApplication
public class XmallFrontWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmallFrontWebApplication.class, args);
    }
}
