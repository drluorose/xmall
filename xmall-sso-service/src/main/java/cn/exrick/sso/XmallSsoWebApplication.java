package cn.exrick.sso;

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
public class XmallSsoWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            XmallSsoWebApplication.class, args);
    }

}
