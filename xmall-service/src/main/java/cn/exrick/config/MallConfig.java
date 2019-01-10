package cn.exrick.config;

import cn.exrick.common.mail.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class MallConfig {

    @Bean
    public Region region(@Value("${aliyun.region.name}") String name,
                         @Value("${aliyun.region.endpointName}") String endpointName,
                         @Value("${aliyun.region.product}") String product,
                         @Value("${aliyun.region.domain}") String domain) {
        return new Region(name, endpointName, product, domain);
    }
}
