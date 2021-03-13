package io.kimmking.rpcfx.client;


import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.proxy.bytebuddy.ByteBuddyHandler;
import io.kimmking.rpcfx.proxy.bytebuddy.ByteBuddyProxy;
import io.kimmking.rpcfx.proxy.jdk.RpcfxInvocationHandler;

import java.lang.reflect.Proxy;

public final class Rpcfx {

    /**
     * JDK 动态代理的方式实现
     */
    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {
        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass},
                new RpcfxInvocationHandler(serviceClass, url, filters));
    }

    /**
     * bytebuddy 实现方法拦截
     */
    public static <T> T createByByteBuddy(final Class<T> tClass, final String url) {
        return ByteBuddyProxy.getProxy(tClass, new ByteBuddyHandler(tClass.getName(), url));
    }
}
