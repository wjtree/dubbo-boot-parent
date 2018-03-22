package com.app.server.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

@Configuration
public class ActiveMQConfig {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("queue.sh");
    }

    @Bean
    public Topic activeMQQueue() {
        return new ActiveMQTopic("topic.sh");
    }

    @Bean("jmsTopicTemplate")
    public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setPubSubDomain(true);
        template.setSessionTransacted(true);
        template.setDefaultDestinationName("topic.sh");
        return template;
    }

    @Bean("jmsQueueTemplate")
    public JmsTemplate jmsQueueTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setPubSubDomain(false);
        template.setSessionTransacted(true);
        template.setDefaultDestinationName("queue.sh");
        return template;
    }
}