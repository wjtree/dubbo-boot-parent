package com.app.data.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class ActiveConfig {
    @Bean
    public Queue queue() {
        return new ActiveMQQueue("queue.sh");
    }

//    @Bean
//    public ActiveMQQueue activeMQQueue() {
//        return new ActiveMQQueue("queue.sh");
//    }
//
//    @Bean("jmsTopicTemplate")
//    public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
//        JmsTemplate template = new JmsTemplate();
//        template.setConnectionFactory(connectionFactory);
//        template.setPubSubDomain(true);
//        template.setSessionTransacted(true);
//        template.setDefaultDestinationName("topic.sh");
//        return template;
//    }
//
    @Bean
    public JmsTemplate jmsQueueTemplate(ConnectionFactory connectionFactory) {
        System.out.println("=======================================22222222222");
        System.out.println("=======================================11111111111111111111111111");
        System.out.println("=======================================3333333");
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory);
        template.setPubSubDomain(false);
        template.setSessionTransacted(true);
        template.setDefaultDestinationName("queue.sh");
        return template;
    }
}