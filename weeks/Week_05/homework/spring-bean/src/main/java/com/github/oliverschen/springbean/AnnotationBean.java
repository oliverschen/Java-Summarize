package com.github.oliverschen.springbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author ck
 * 基于注解获取 bean
 */
public class AnnotationBean {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("com.github.oliverschen.springbean");
        User user001 = (User) context.getBean("user001");
        user001.setName("ck");
        user001.setPhone("8888888");
        System.out.println(String.format("user001: ==>%s", user001.toString()));
        System.out.println();

        School beanSchool = (School) context.getBean("beanSchool");
        System.out.println(String.format("beanSchool: ==>%s", beanSchool.toString()));
    }
}
