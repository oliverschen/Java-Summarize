package com.github.oliverschen;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * CyclicBarrier 实现
 */
public class V4 extends AbstractBase {

    public static void main(String[] args) {
        new V4().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        AtomicInteger result = new AtomicInteger();
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                result.set(sum());
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        barrier.await();
        return result.get();
    }
}
