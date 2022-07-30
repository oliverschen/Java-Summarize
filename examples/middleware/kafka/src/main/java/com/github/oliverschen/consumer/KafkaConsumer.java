package com.github.oliverschen.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"user_info_topic"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional.ofNullable(record.value()).ifPresent(msg -> log.info("consume success：{}", msg));
    }

}
