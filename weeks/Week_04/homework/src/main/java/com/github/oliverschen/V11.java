package com.github.oliverschen;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ck
 * Lock & Condition 实现
 */
public class V11 extends AbstractBase {

    public static void main(String[] args) {
        new V11().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        AtomicInteger result = new AtomicInteger();
        new Thread(() -> {
           try {
               lock.lock();
               result.set(sum());
               condition.signal();
           }finally {
               lock.unlock();
           }
        }).start();
        try {
            lock.lock();
            condition.await();
        }finally {
            lock.unlock();
        }
        return result.get();
    }
}
