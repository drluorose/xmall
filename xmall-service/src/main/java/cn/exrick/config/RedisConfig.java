package cn.exrick.config;

import cn.exrick.common.jedis.JedisClientPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class RedisConfig {

    @Bean
    public JedisClientPool jedisClientPool(@Value("${redis.host}") String host,
                                           @Value("${redis.port}") int port) {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setTestOnBorrow(true);
        JedisPool jedisPool = new JedisPool(genericObjectPoolConfig, host, port);
        JedisClientPool jedisClientPool = new JedisClientPool();
        jedisClientPool.setJedisPool(jedisPool);
        return jedisClientPool;
    }
}
