package cn.exrick.manager.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    private OssConfig oss;

}
