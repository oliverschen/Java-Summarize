package com.jihe.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class JiheEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiheEurekaApplication.class, args);
    }

}
