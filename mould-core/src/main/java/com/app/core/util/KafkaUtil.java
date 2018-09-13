package com.app.core.util;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Log4j2
public class KafkaUtil {
    /**
     * Kafka 操作模板
     */
    private static KafkaTemplate<String, String> template;

    static {
        template = IocUtil.getBean(KafkaTemplate.class);
    }

    /**
     * 发送消息到默认主题
     * <p>不对消息发送结果进行处理时使用</p>
     * <p>此方法调用前提是为模板设置了默认主题，
     * 通过设置“spring.kafka.template.default-topic”参数或调用 setDefaultTopic 方法</p>
     *
     * @param data 消息内容
     */
    public static void sendDefault(String data) {
        template.sendDefault(data);
    }

    /**
     * 发送消息到默认主题
     * <p>此方法调用前提是为模板设置了默认主题，
     * 通过设置“spring.kafka.template.default-topic”参数或调用 setDefaultTopic 方法</p>
     *
     * @param data 消息内容
     */
    public static void sendDefaultAsync(String data) {
        template.sendDefault(data).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(data, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(data, ex);
            }
        });
    }

    /**
     * 发送消息到指定主题
     * <p>不对消息发送结果进行处理时使用</p>
     *
     * @param topic 主题
     * @param data  消息内容
     */
    public static void send(String topic, String data) {
        template.send(topic, data);
    }

    /**
     * 发送消息到指定主题
     * <p>异步处理消息发送结果</p>
     *
     * @param topic 主题
     * @param data  消息内容
     */
    public static void sendAsync(String topic, String data) {
        template.send(topic, data).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                handleSuccess(data, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                handleFailure(data, ex);
            }
        });
    }

    /**
     * 发送消息到指定主题
     * <p>同步处理消息发送结果</p>
     *
     * @param topic 主题
     * @param data  消息内容
     */
    public static void sendSync(String topic, String data) {
        try {
            // 如果希望阻止发送线程，等待结果，可以调用future的get()方法
            SendResult<String, String> result = template.send(topic, data).get(10, TimeUnit.SECONDS);

            handleSuccess(data, result);
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            handleFailure(data, e.getCause());
        }
    }

    /**
     * 消息发送成功时的处理
     *
     * @param data   消息内容
     * @param result {@link SendResult}
     */
    private static void handleSuccess(String data, SendResult<String, String> result) {
        // 获取 kafka 服务器的主题，分区，时间戳等信息
        RecordMetadata metadata = result.getRecordMetadata();
        String topic = metadata.topic();
        int partition = metadata.partition();
        long timestamp = metadata.timestamp();

        if (log.isInfoEnabled()) {
            log.info("Kafka 消息发送成功，主题 topic：{}，分区 partition：{}，时间 timestamp：{}，消息内容：{}",
                    topic, partition, timestamp, data);
        }
    }

    /**
     * 消息发送失败时的处理
     *
     * @param data 消息内容
     * @param ex   异常栈
     */
    private static void handleFailure(String data, Throwable ex) {
        log.error("Kafka 消息发送失败，消息内容：{}", data, ex);
    }
}
