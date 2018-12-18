package cn.exrick.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author dongjiejie
 */
@EnableWebMvc
@SpringBootApplication
public class XmallFrontWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(XmallFrontWebApplication.class, args);
    }
}
