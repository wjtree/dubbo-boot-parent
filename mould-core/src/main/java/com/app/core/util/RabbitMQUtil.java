package com.app.core.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Log4j2
public class RabbitMQUtil {
    /**
     * RabbitMQ消息发送和接收模板
     */
    private static RabbitTemplate template;

    static {
        template = IocUtil.getBean(RabbitTemplate.class);
    }

    /**
     * 发送消息
     *
     * @param message 消息内容
     */
    public static void send(Object message) {
        try {
            template.convertAndSend(message);
        } catch (Exception e) {
            log.error("RabbitMQ 消息发送失败", e);
        }
    }
}