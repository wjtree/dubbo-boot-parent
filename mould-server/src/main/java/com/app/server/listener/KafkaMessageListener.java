package com.app.server.listener;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Log4j2
@Component
public class KafkaMessageListener {
    @KafkaListener(topics = "kafka-topic-def")
    @SendTo // use default replyTo expression
    public String replyingDeflistener(String in) {
        System.out.println("Server received: " + in);
        return in.toUpperCase();
    }

    @KafkaListener(topics = "kafka-topic-def")
    @SendTo("kafka-topic-def") // static reply topic definition
    public String replyinglistener(String in) {
        System.out.println("Server received: " + in);
        return in.toUpperCase();
    }

    @KafkaListener(topics = "kafka-topic-def")
    @SendTo("kafka-topic-def") // static reply topic definition
    public Collection<String> replyingBatchListener(List<String> in) {
        System.out.println("Server received: " + in);
        return in;
    }

    @KafkaListener(topics = "kafka-topic-def", errorHandler = "kafkaListenerHandler")
    @SendTo("kafka-topic-def") // static reply topic definition
    public String replyingListenerWithErrorHandler(String in) {
        System.out.println("Server received: " + in);
        return in.toUpperCase();
    }

    @KafkaListener(topicPattern = "kafka-topic-def")
    public void listen(@Payload String foo,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Integer key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {

        System.out.println("Server received: " + foo);
    }

    @KafkaListener(topics = "kafka-topic-def")
    public void batchListen(List<String> list,
                            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        System.out.println("Server received: " + list);
    }

    @KafkaListener(topics = "myTopic")
    public void listen(ConsumerRecord<?, ?> cr) {
        log.info(cr.toString());
    }
}
