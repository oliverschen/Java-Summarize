package com.github.oliverschen.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ck
 * Java 动态代理 AOP
 */
public class LogAOP implements InvocationHandler {

    public static void main(String[] args) {
        IOrderService orderService = new OrderServiceImpl();
        IOrderService o = (IOrderService) Proxy.newProxyInstance(
                orderService.getClass().getClassLoader(),
                orderService.getClass().getInterfaces(),
                new LogAOP(orderService)
        );
        o.placeOrder();
    }


    private Object object;

    public LogAOP(Object o) {
        this.object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        long startTime = doBefore();

        Object result = method.invoke(this.object, args);

        doAfter(startTime);
        return result;
    }

    private void doAfter(long startTime) {
        System.out.println();
        System.out.println("AOP 结束调用，总共消耗：" + (System.currentTimeMillis() - startTime) + " ms");
    }

    private long doBefore() {
        long l = System.currentTimeMillis();
        System.out.println("AOP 开始调用时间：" + l);
        System.out.println();
        return l;
    }
}
