package @packageName@.config;

import com.douyu.wsd.framework.oa.config.OaConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "OAService")
@Data
public class DouyuOaConfig extends OaConfig {

}
