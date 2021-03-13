package com.github.oliverschen.dynamic.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author ck
 */
public class DynamicRouting extends AbstractRoutingDataSource {

    /**
     * 动态数据路由
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource();
    }
}
