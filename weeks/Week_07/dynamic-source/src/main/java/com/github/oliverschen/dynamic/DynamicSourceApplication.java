package com.github.oliverschen.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DynamicSourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicSourceApplication.class, args);
    }

}
