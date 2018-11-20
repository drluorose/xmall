package cn.exrick.config;

import cn.exrick.search.message.ItemESMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * @author dongjiejie dongjiejie@qq.com
 */
@Configuration
public class SearchConfig {

    @Value("${activemq.brokerUrl}")
    private String brokerUrl;

    @Autowired
    private ItemESMessageListener itemESMessageListener;

    @Autowired
    private SingleConnectionFactory singleConnectionFactory;

    @Autowired
    private ActiveMQTopic activeMQTopic;

    @Bean
    public ItemESMessageListener itemESMessageListener() {
        return new ItemESMessageListener();
    }

    @Bean
    public DefaultMessageListenerContainer itemMessageListenerContainer() {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setConnectionFactory(singleConnectionFactory);
        defaultMessageListenerContainer.setDestination(activeMQTopic);
        defaultMessageListenerContainer.setMessageListener(itemESMessageListener);
        return defaultMessageListenerContainer;
    }

}
