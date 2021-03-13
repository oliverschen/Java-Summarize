package com.github.oliverschen.homework.guava;

import com.google.common.util.concurrent.*;

import javax.annotation.Nullable;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * guava 并发
 */
public class CallbackTest {

    public static void main(String[] args) {
        ExecutorService executor = buildExecutorService();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(executor);
        ListenableFuture<?> listenableFuture = service.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        Futures.addCallback(listenableFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                System.out.println("子线程执行结束");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.format("子线程执行出错：%s",t.getMessage());
            }
        }, executor);
        System.out.println("主线程已经结束");
    }

    private static ExecutorService buildExecutorService() {
        return new ThreadPoolExecutor(5, 10, 30,
                TimeUnit.MINUTES, new LinkedBlockingDeque<>(200),
                r -> {
                    AtomicInteger a = new AtomicInteger();
                    Thread thread = new Thread(r);
                    thread.setDaemon(false);
                    thread.setName("my---" + a.getAndIncrement());
                    return thread;
                });
    }
}
