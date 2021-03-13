package com.github.oliverschen.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.oliverschen.mq.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
class MqAllApplicationTests {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper mapper = new ObjectMapper();
    @Test
    void contextLoads() throws JsonProcessingException {
        for (int i = 0; i < 10; i++) {
            Msg message = new Msg();
            message.setId(System.currentTimeMillis());
            message.setMsg("消息 ：" + i);
            message.setSendTime(LocalDateTime.now());
            log.info("+++++++++++++++++++++  message = {}", message.toString());
            kafkaTemplate.send("jihe", mapper.writeValueAsString(message));
        }
    }

}
