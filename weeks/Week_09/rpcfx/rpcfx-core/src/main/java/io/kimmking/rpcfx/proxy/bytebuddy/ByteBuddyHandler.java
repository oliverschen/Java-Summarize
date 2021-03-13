package io.kimmking.rpcfx.proxy.bytebuddy;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.util.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author ck
 */
@Slf4j
public class ByteBuddyHandler {

    private final String className;
    private final String url;

    public ByteBuddyHandler(String className,String url) {
        this.className = className;
        this.url = url;
    }

    @RuntimeType
    public Object intercept(@AllArguments Object[] allArguments,
                            @Origin Method method) throws IOException {
        // intercept any method of any signature
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(className);
        request.setMethod(method.getName());
        request.setParams(allArguments);

        RpcfxResponse response = OkHttpUtil.post(request, url);

        // 加filter地方之三
        // Student.setTeacher("cuijing");

        // 这里判断response.status，处理异常
        // 考虑封装一个全局的RpcfxException
        if (!response.isStatus()) {
            return response.getException();
        }

        return JSON.parse(response.getResult().toString());
    }




}
