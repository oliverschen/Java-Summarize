package com.github.oliverschen;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * Semaphore 实现
 */
public class V5 extends AbstractBase {

    public static void main(String[] args) {
        new V5().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        Semaphore s = new Semaphore(0);
        AtomicInteger result = new AtomicInteger();
        new Thread( () -> {
            result.set(sum());
            s.release();
        } ).start();
        try {
            s.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return result.get();

    }
}