package com.github.oliverschen;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author ck
 * BlockingQueue 实现
 */
public class V10 extends AbstractBase{

    public static void main(String[] args) {
        new V10().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        new Thread(() -> {
            try {
                queue.put(sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }).start();
        return queue.take();
    }
}
