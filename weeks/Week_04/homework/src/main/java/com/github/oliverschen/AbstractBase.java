package com.github.oliverschen;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author ck
 * 统一基础类
 */
public abstract class AbstractBase {

    public abstract int asyncInvoke() throws Exception;


    public void template() {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = 0;
        try {
            result = asyncInvoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }


    public static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }


    /**
     * 自定义线程池
     */
    public ThreadPoolExecutor getPool(Integer poolSize, Integer queueSize) {
        AtomicInteger num = new AtomicInteger(0);
        return new ThreadPoolExecutor(1, poolSize, 60L, SECONDS,
                new LinkedBlockingDeque<>(queueSize),
                r -> {
                    Thread t = new Thread(r);
                    t.setName("my-" + num.incrementAndGet());
                    t.setDaemon(false);
                    return t;
                });
    }

}
