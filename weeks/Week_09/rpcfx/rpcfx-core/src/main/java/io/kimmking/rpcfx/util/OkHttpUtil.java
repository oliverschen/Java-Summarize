package io.kimmking.rpcfx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.Objects;

/**
 * @author ck
 */
@Slf4j
public class OkHttpUtil {

    static {
        ParserConfig.getGlobalInstance().addAccept("io.kimmking");
    }
    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");

    public static RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        log.info("req json: {}", reqJson);

        // 1.可以复用client
        // 2.尝试使用httpclient或者netty client
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = Objects.requireNonNull(client.newCall(request).execute().body()).string();
        log.info("resp json: {}", respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

}
