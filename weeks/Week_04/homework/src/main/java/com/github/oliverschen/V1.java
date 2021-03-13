package com.github.oliverschen;

import java.util.concurrent.CompletableFuture;

/**
 * @author ck
 * 使用 CompletableFutrue 实现
 */
public class V1 extends AbstractBase{

    public static void main(String[] args) {
        new V1().template();
    }


    @Override
    public int asyncInvoke() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(AbstractBase::sum);
        return future.get();
    }
}
