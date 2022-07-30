package com.github.oliverschen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Hello world!
 *
 */
@EnableKafka
@SpringBootApplication
public class KafkaApp {
    public static void main( String[] args ) {
        SpringApplication.run(KafkaApp.class,args);
    }
}
