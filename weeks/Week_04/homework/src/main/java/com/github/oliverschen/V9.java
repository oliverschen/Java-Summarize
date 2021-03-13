package com.github.oliverschen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author ck
 * LockSupport 实现
 */
public class V9 extends AbstractBase {

    public static void main(String[] args) {
        new V9().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        AtomicInteger result = new AtomicInteger();
        Thread main = Thread.currentThread();
        new Thread(() -> {
            try {
                result.set(sum());
            }finally {
                LockSupport.unpark(main);
            }
        }).start();
        LockSupport.park();
        return result.get();
    }
}
