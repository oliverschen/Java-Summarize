package com.github.oliverschen.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class KafkaMsg {

    public KafkaMsg() {}

    public KafkaMsg(String topic) {
        this.topic = topic;
    }

    private String msgId = UUID.randomUUID().toString();
    private String body;
    private String topic;
}
