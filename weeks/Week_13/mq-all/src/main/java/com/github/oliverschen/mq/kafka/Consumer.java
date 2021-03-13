package com.github.oliverschen.mq.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.System.out;

/**
 * @author ck
 */
@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = {"jihe"})
    public void listen(ConsumerRecord<?, ?> record) {

        Optional.ofNullable(record.value()).ifPresent(out::println);

        log.info("record::::{}", record);
    }
}
