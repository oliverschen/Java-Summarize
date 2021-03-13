package com.github.oliverschen.springbean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author ck
 */
@Aspect
@Component
public class AopLog{

    @Pointcut("execution(* com.github.oliverschen.springbean..*())")
    public void point() {}

    @Before("point()")
    public void before(JoinPoint joinPoint) {
        System.out.println("before 方法");
    }

    @After("point()")
    public void after() {
        System.out.println("after 方法");
    }

    @AfterReturning("point()")
    public void ret() {
        System.out.println("return 方法");
    }

    @AfterThrowing("point()")
    public void trw() {
        System.out.println("AfterThrowing 方法");
    }

    @Around("point()")
    public void around(ProceedingJoinPoint joinPoint) {
        System.out.println("around 方法进入");
        try {
            joinPoint.proceed();
            System.out.println("around 方法完成");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
