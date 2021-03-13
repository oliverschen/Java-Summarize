package com.github.oliverschen;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 * join 实现
 */
public class V8 extends AbstractBase {

    public static void main(String[] args) {
        new V8().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        AtomicInteger result = new AtomicInteger();
        Thread thread = new Thread(() -> result.set(sum()));
        thread.start();
        thread.join();
        return result.get();
    }
}
