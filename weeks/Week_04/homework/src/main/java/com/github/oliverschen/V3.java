package com.github.oliverschen;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * CountDownLatch 实现
 */
public class V3 extends AbstractBase{

    public static void main(String[] args) {
        new V3().template();
    }


    @Override
    public int asyncInvoke() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger result = new AtomicInteger();
        new Thread(() -> {
            try {
                result.set(sum());
            }finally {
                latch.countDown();
            }
        }).start();
        latch.await();
        return result.get();
    }
}
