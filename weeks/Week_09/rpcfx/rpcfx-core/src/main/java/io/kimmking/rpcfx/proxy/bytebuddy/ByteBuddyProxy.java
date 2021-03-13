package io.kimmking.rpcfx.proxy.bytebuddy;

import io.kimmking.rpcfx.exception.RpcfxException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author ck
 */
@Slf4j
public class ByteBuddyProxy {

    public static <T> T getProxy(Class<T> tClass,Object handler) {
        Class<?> clazz = new ByteBuddy()
                .subclass(Object.class)
                .implement(tClass)
                .method(ElementMatchers.isDeclaredBy(tClass))
                .intercept(MethodDelegation.to(handler))
                .make()
                .load(tClass.getClassLoader())
                .getLoaded();
        try {
            if (clazz == null) {
                throw new RpcfxException("create instance error on bytebyddy");
            }
            return (T) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RpcfxException("bytebuddy create proxy error", e);
        }
    }
}
