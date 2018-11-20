package cn.exrick.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "es")
public class EsConfig {
    private String host;

    private Integer port;

    private String username;

    private String password;

    private String searchItemIndexName;

    private String searchItemIndexType;
}
