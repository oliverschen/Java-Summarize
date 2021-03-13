package com.github.oliverschen;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ck
 * FutureTask 实现
 */
public class V2 extends AbstractBase{

    public static void main(String[] args) {
        new V2().template();
    }


    @Override
    public int asyncInvoke() throws Exception {
        FutureTask<Integer> task = new FutureTask<>(AbstractBase::sum);
        new Thread(task).start();
        return task.get();
    }
}
