package com.github.oliverschen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class KafkaApp {
    public static void main( String[] args ) {
        SpringApplication.run(KafkaApp.class,args);
    }
}
