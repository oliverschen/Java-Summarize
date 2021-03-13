package com.github.oliverschen.springbean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ck
 * 基于 xml 获取 bean
 */
public class XmlBean {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student001 = (Student) context.getBean("student001");
        System.out.println(String.format("student001 ==>%s", student001.toString()));
        Student student002 = (Student) context.getBean("student002");
        System.out.println(String.format("student002 ==>%s", student002.toString()));

        School school = (School) context.getBean("school");
        System.out.println(String.format("school: ==>%s",school.toString()));
    }
}
