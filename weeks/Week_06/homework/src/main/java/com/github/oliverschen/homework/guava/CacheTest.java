package com.github.oliverschen.homework.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author ck
 * guava 缓存
 */
public class CacheTest {

    public static void main(String[] args) throws ExecutionException {

        Cache<String, String> cache = buildCache();
        cache.put("key1", "111");
        cache.put("key2", "222");
        cache.put("key3", "333");
        cache.invalidate("key1");

        // 如果存在 key 则直接返回，不存在则异步去获取
        System.out.println(cache.get("key2", () -> "hehe"));
    }

    private static Cache<String, String> buildCache() {
        return CacheBuilder.newBuilder()
                // 缓存大小
                .maximumSize(2)
                // 过期时间
                .expireAfterWrite(2, TimeUnit.SECONDS)
                // 添加移除监听
                .removalListener(new CacheListener())
                .build();
    }

    /**
     * cache 移除监听
     */
    public static class CacheListener implements RemovalListener<String,String>{
        @Override
        public void onRemoval(RemovalNotification<String, String> notification) {
            System.out.format("删除%s,",notification);
        }
    }


}
