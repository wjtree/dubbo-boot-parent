package com.app.server.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;
import java.util.Map;

@Log4j2
@Component
public class ActiveMQListener {
    @JmsListener(destination = "topic.sh", containerFactory = "topicContainer")
    public void receiveTopic(@Headers Map headers, @Payload Object payload) {
        try {
            String message = payload instanceof TextMessage ? ((TextMessage) payload).getText() : JSON.toJSONString(payload);

            log.info("消息头：{}，消息体：{}", headers, message);
        } catch (Exception e) {
            log.error("ActiveMQ 消息解析出错", e);
        }
    }

    @JmsListener(destination = "queue.sh", containerFactory = "queueContainer")
    public void receiveQueue(@Headers Map headers, @Payload Object payload) {
        try {
            String message = payload instanceof TextMessage ? ((TextMessage) payload).getText() : JSON.toJSONString(payload);

            log.info("消息头：{}，消息体：{}", headers, message);
        } catch (Exception e) {
            log.error("ActiveMQ 消息解析出错", e);
        }
    }
}