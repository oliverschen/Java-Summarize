package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxResolver;

/**
 * 在 rpcContext 中获取 bean
 *
 * @author ck
 */
public class MapResolver implements RpcfxResolver {

    @Override
    public Object resolve(String serviceClass) {
        return RpcContext.getBean(serviceClass);
    }
}
