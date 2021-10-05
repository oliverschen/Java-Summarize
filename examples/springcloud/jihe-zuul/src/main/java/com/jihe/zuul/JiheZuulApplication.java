package com.jihe.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class JiheZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiheZuulApplication.class, args);
    }

}
