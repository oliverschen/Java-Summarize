package com.jihe.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class JiheUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiheUserApplication.class, args);
    }

}
