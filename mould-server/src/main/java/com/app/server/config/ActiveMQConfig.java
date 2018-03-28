package com.app.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Session;

@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.topic.name}")
    private String topicName;
    @Value("${spring.activemq.queue.name}")
    private String queueName;

    @Bean("topicTemplate")
    public JmsTemplate topicTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionTransacted(true);
        template.setPubSubDomain(true);
        template.setDefaultDestinationName(topicName);
        return template;
    }

    @Bean("queueTemplate")
    public JmsTemplate queueTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionTransacted(true);
        template.setPubSubDomain(false);
        template.setDefaultDestinationName(queueName);
        return template;
    }

    @Bean("topicContainer")
    public DefaultJmsListenerContainerFactory topicContainer(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 事务控制（开启后会显著影响消费者性能），默认为false，超过重发次数（缺省为6次）后会发送到死信队列，默认为ActiveMQ.DLQ
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setSessionTransacted(false);
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean("queueContainer")
    public DefaultJmsListenerContainerFactory queueContainer(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        // 事务控制（开启后会显著影响消费者性能），默认为false，超过重发次数（缺省为6次）后会发送到死信队列，默认为ActiveMQ.DLQ
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        factory.setSessionTransacted(false);
        return factory;
    }
}