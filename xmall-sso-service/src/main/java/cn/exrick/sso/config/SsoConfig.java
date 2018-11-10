package cn.exrick.sso.config;

import cn.exrick.common.jedis.JedisClientPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class SsoConfig {

    @Bean
    public JedisClientPool jedisClientPool(@Value("${redis.host}") String host,
                                           @Value("${redis.port}") int port) {
        JedisPool jedisPool = new JedisPool(host, port);
        JedisClientPool jedisClientPool = new JedisClientPool();
        jedisClientPool.setJedisPool(jedisPool);
        return jedisClientPool;
    }
}
