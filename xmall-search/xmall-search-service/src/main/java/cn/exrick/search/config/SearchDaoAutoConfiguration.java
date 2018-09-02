package cn.exrick.search.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan(value = {"cn.exrick.manager.mapper"})
@Slf4j
public class SearchDaoAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    public DataSource getOrderDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public PageHelper pageHelper() {
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dspDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dspDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath*:/cn/exrick/manager/mapper/**/*.xml"));
        factoryBean.setPlugins(new Interceptor[]{pageHelper()});
        return factoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Autowired DataSource dspDataSource) {
        return new DataSourceTransactionManager(dspDataSource);
    }

    @Bean
    public TransactionTemplate transactionTemplate(@Autowired DataSourceTransactionManager dspTransactionManager) {
        return new TransactionTemplate(dspTransactionManager);
    }
}
