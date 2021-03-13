package io.gihub.oliverschen.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class OkhttpClient {

    public static final String URL = "http://localhost:8888/hello";

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
            .build();

    public static String get(String url) {

        Request request = new Request.Builder().url(url).build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(get(URL));
    }
}
