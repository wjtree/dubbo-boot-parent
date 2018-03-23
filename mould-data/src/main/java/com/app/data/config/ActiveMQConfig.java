package com.app.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
public class ActiveMQConfig {
    @Bean("topicTemplate")
    public JmsTemplate topicTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionTransacted(true);
        template.setPubSubDomain(true);
        template.setDefaultDestinationName("topic.sh");
        return template;
    }

    @Bean("queueTemplate")
    public JmsTemplate queueTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setSessionTransacted(true);
        template.setPubSubDomain(false);
        template.setDefaultDestinationName("queue.sh");
        return template;
    }
}