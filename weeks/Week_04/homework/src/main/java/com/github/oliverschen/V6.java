package com.github.oliverschen;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * wait & notify
 */
public class V6 extends AbstractBase {

    public static void main(String[] args) {
        new V6().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        Object o = new Object();
        AtomicInteger result = new AtomicInteger();
        new Thread(() -> {
            synchronized (o) {
                result.set(sum());
                o.notify();
            }
        }).start();
        synchronized (o) {
            o.wait();
        }
        return result.get();
    }
}
