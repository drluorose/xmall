package cn.exrick.manager.web.config;

import cn.exrick.manager.web.filter.CrossDomainFilter;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mall")
public class MallConfig {

    private String corsDomainPattern;

    @Bean
    public FilterRegistrationBean corssDoaminFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CrossDomainFilter(corsDomainPattern));
        filterRegistrationBean.setUrlPatterns(Lists.newArrayList("/*"));
        return filterRegistrationBean;
    }
}
