package com.app.core.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

@Log4j2
public class ActiveMQUtil {
    /**
     * Spring JMS Topic 操作模板
     */
    private static JmsTemplate topicTemplate;
    /**
     * Spring JMS Queue 操作模板
     */
    private static JmsTemplate queueTemplate;

    static {
        topicTemplate = IocUtil.getBean("topicTemplate", JmsTemplate.class);
        queueTemplate = IocUtil.getBean("queueTemplate", JmsTemplate.class);
    }

    /**
     * 发送JMS Topic消息，使用默认目的地
     *
     * @param message
     */
    public static void sendTopicMsg(Object message) {
        try {
            topicTemplate.convertAndSend(message);
        } catch (Exception e) {
            log.error("发送JMS Topic消息失败", e);
        }
    }

    /**
     * 发送JMS Topic消息
     *
     * @param destinationName
     * @param message
     */
    public static void sendTopicMsg(String destinationName, final Object message) {
        try {
            topicTemplate.convertAndSend(destinationName, message);
        } catch (Exception e) {
            log.error("发送JMS Topic消息失败", e);
        }
    }

    /**
     * 发送JMS Topic消息
     *
     * @param destination
     * @param message
     */
    public static void sendTopicMsg(Destination destination, final Object message) {
        try {
            topicTemplate.convertAndSend(destination, message);
        } catch (Exception e) {
            log.error("发送JMS Topic消息失败", e);
        }
    }

    /**
     * 发送JMS Queue消息，使用默认目的地
     *
     * @param message
     */
    public static void sendQueueMsg(Object message) {
        try {
            queueTemplate.convertAndSend(message);
        } catch (Exception e) {
            log.error("发送JMS Queue消息失败", e);
        }
    }

    /**
     * 发送JMS Queue消息
     *
     * @param destinationName
     * @param message
     */
    public static void sendQueueMsg(String destinationName, final Object message) {
        try {
            queueTemplate.convertAndSend(destinationName, message);
        } catch (Exception e) {
            log.error("发送JMS Queue消息失败", e);
        }
    }

    /**
     * 发送JMS Queue消息
     *
     * @param destination
     * @param message
     */
    public static void sendQueueMsg(Destination destination, final Object message) {
        try {
            queueTemplate.convertAndSend(destination, message);
        } catch (Exception e) {
            log.error("发送JMS Queue消息失败", e);
        }
    }
}