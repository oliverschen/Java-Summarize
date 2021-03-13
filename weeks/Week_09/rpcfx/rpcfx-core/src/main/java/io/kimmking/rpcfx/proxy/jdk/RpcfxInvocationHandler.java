package io.kimmking.rpcfx.proxy.jdk;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.Filter;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.util.OkHttpUtil;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author ck
 */
public class RpcfxInvocationHandler implements InvocationHandler {

    private final Class<?> serviceClass;
    private final String url;
    private final Filter[] filters;

    public <T> RpcfxInvocationHandler(Class<T> serviceClass, String url, Filter... filters) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.filters = filters;
    }

    // 可以尝试，自己去写对象序列化，二进制还是文本的，，，rpcfx是xml自定义序列化、反序列化，json: code.google.com/p/rpcfx
    // int byte char float double long bool
    // [], data class

    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws IOException {

        // 加filter地方之二
        // mock == true, new Student("hubao");

        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(params);

        RpcfxResponse response = OkHttpUtil.post(request, url);
        // 加filter地方之三
        // Student.setTeacher("cuijing");

        // 这里判断response，status，处理异常
        // 考虑封装一个全局的RpcfxException
        if (!response.isStatus()) {
            throw response.getException();
        }
        return JSON.parse(response.getResult().toString());
    }
}
