package com.github.oliverschen.dynamic.config;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ck
 * datasource holder 将当前线程使用的数据源保存在 ThreadLocal
 */
@Slf4j
public class DataSourceHolder {

    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    /**
     * 保存从节点，做负载均衡使用
     */
    private static final HashMap<Integer,String> secondaryMap = new HashMap<>(4);

    public static void setDataSource(String dsType) {
        HOLDER.set(dsType);
    }

    public static String getDataSource() {
       return HOLDER.get();
    }

    public static void clearDataSource() {
        HOLDER.remove();
    }

    /**
     * 设置所有从库数据源
     */
    public static void setSecondaryDs(Map<Integer, String> dsMap) {
        secondaryMap.putAll(dsMap);
    }

    /**
     * 获取从节点的数据源
     */
    public static String getSecondaryDs() {
        int dsIndex = ThreadLocalRandom.current().nextInt(secondaryMap.size());
        log.debug("===》获取到的从库索引：{}",dsIndex);
        return secondaryMap.get(dsIndex);
    }
}
