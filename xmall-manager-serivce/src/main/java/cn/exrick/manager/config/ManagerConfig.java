package cn.exrick.manager.config;

import cn.exrick.common.jedis.JedisClientPool;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import redis.clients.jedis.JedisPool;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class ManagerConfig {

    @Bean
    public SingleConnectionFactory singleConnectionFactory(@Value("${activemq.brokerUrl}") String brokerURL) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(brokerURL);
        SingleConnectionFactory singleConnectionFactory = new SingleConnectionFactory();
        singleConnectionFactory.setTargetConnectionFactory(activeMQConnectionFactory);
        return singleConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(@Autowired SingleConnectionFactory singleConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(singleConnectionFactory);
        return jmsTemplate;
    }

    @Bean
    public ActiveMQQueue activeMQQueue() {
        return new ActiveMQQueue("spring-queue");
    }

    @Bean
    public ActiveMQTopic activeMQTopic() {
        return new ActiveMQTopic("itemAddTopic");
    }

    @Bean
    public JedisClientPool jedisClientPool(@Value("${redis.host}") String host,
                                           @Value("${redis.port}") int port) {
        JedisPool jedisPool = new JedisPool(host, port);
        JedisClientPool jedisClientPool = new JedisClientPool();
        jedisClientPool.setJedisPool(jedisPool);
        return jedisClientPool;
    }
}
