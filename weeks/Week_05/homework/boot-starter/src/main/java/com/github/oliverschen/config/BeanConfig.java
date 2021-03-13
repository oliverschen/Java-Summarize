package com.github.oliverschen.config;

import com.github.oliverschen.bean.School;
import com.github.oliverschen.bean.Student;
import com.github.oliverschen.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ck
 */
@Configuration
public class BeanConfig {

    @Bean(name = "bootStudent")
    public Student student() {
        return new Student("ck",18);
    }

    @Bean(name = "bootSchool")
    public School school() {
        return new School("北大","peking",new Student("fz",17));
    }

    @Bean(name = "bootUser")
    public User user() {
        return new User("ck","888888888");
    }
}
