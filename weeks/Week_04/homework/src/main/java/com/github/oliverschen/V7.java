package com.github.oliverschen;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ck
 * Future 实现
 */
public class V7 extends AbstractBase {

    public static void main(String[] args) {
        new V7().template();
    }

    @Override
    public int asyncInvoke() throws Exception {
        ThreadPoolExecutor pool = getPool(1, 1);
        Future<Integer> result = pool.submit(AbstractBase::sum);
        return result.get();
    }
}
