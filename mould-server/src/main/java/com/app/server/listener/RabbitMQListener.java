package com.app.server.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.CharEncoding;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Log4j2
@Component
public class RabbitMQListener {
    @RabbitListener(queues = {"queue.sz"})
    public void receive(@Headers Map headers, @Payload Object payload) {
        try {
            // 将 byte 数组转换为字符串
            String message = payload instanceof Message ? new String(((Message) payload).getBody(), CharEncoding.UTF_8) : JSON.toJSONString(payload);

            log.info("消息头：{}，消息体：{}", headers, message);
        } catch (Exception e) {
            log.error("RabbitMQ 消息解析出错", e);
        }
    }
}