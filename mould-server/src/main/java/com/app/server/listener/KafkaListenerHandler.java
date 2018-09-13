package com.app.server.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaListenerHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> msg, ListenerExecutionFailedException ex) {
        log.error("Kafka 消息监听出错", ex);
        return null;
    }
}
