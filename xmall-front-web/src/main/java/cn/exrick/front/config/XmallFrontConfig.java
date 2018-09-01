package cn.exrick.front.config;

import cn.exrick.common.jedis.JedisClientPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import redis.clients.jedis.JedisPool;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class XmallFrontConfig {
    @Value("${xmall.redis.host}")
    private String host;

    @Value("${xmall.redis.port}")
    private int port;

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(host, port);
    }

    @Bean
    public JedisClientPool jedisClientPool() {
        JedisClientPool jedisClientPool = new JedisClientPool();
        jedisClientPool.setJedisPool(jedisPool());
        return jedisClientPool;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(5242880);
        return multipartResolver;
    }

}
