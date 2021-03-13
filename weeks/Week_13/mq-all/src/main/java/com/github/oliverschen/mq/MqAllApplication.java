package com.github.oliverschen.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.oliverschen.mq.entity.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@RestController
@SpringBootApplication
public class MqAllApplication {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        SpringApplication.run(MqAllApplication.class, args);
    }


    @GetMapping("/kafka")
    public void kafkaProducer() throws JsonProcessingException {
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
