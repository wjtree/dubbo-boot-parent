package com.app.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

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
}