package com.github.oliverschen.springbean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ck
 */
@Configuration
public class ConfigBean {

    @Bean(name = "beanSchool")
    public School school() {
        Student student = new Student("ccckkk", 25);
        return new School("@Bean school","@Bean 创建",student);
    }
}
