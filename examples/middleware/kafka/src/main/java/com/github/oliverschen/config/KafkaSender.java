package com.github.oliverschen.config;

import com.github.oliverschen.domain.KafkaMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class KafkaSender {


    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String body) {
        KafkaMsg msg = new KafkaMsg("user_info_topic");
        msg.setBody(body);
        kafkaTemplate.send("user_info_topic", msg.toString());

    }
}
