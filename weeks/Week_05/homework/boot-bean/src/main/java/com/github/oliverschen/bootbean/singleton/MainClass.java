package com.github.oliverschen.bootbean.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ck
 * 主测试类
 */
public class MainClass {

    public static void main(String[] args) {
        // 饿汉式
        HungrySingleton instance = HungrySingleton.getInstance();
        System.out.println(instance);
        // 懒汉式
        LazySingleton lazy = LazySingleton.getInstance();
        System.out.println(Thread.currentThread().getName() + " lazy:" + lazy);

        ExecutorService service = Executors.newFixedThreadPool(5);
        service.execute(()->{
            LazySingleton instance1 = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " lazy:" + instance1);
        });

        service.execute(()->{
            LazySingleton instance1 = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " lazy:" + instance1);
        });

        service.execute(()->{
            StaticClassSingleton instance1 = StaticClassSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "static-class" + instance1);
        });

        service.execute(()->{
            EnumSingleton instance1 = EnumSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + "enum-class" + instance1);
        });
        service.shutdown();
    }
}
