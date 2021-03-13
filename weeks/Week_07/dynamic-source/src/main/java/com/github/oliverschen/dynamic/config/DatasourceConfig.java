package com.github.oliverschen.dynamic.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ck
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "com.github.oliverschen.dynamic.mapper",
        sqlSessionTemplateRef = "sqlSessionTemplate",
        sqlSessionFactoryRef = "sqlSessionFactory")
public class DatasourceConfig {

    private static final String PRIMARY_DS = "primaryDs";
    private static final String SECONDARY_DS = "secondaryDs";
    private static final String DYNAMIC_DS = "dynamicDs";
    private static final String MAPPER_PATH = "classpath:mapper/*.xml";


    @Bean(PRIMARY_DS)
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDs() {
        return DataSourceBuilder.create().build();
    }

    @Bean(SECONDARY_DS)
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource secondaryDs() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     */
    @Bean(DYNAMIC_DS)
    public DataSource dynamicRouting(@Qualifier(PRIMARY_DS) DataSource primaryDs,
                                     @Qualifier(SECONDARY_DS) DataSource secondaryDs) {
        DynamicRouting ds = new DynamicRouting();
        Map<Object, Object> dsMap = new HashMap<>(4);
        dsMap.put(PRIMARY_DS, primaryDs);
        dsMap.put(SECONDARY_DS, secondaryDs);
        ds.setTargetDataSources(dsMap);
        // 默认从库
        ds.setDefaultTargetDataSource(secondaryDs);

        // 指定所有从库数据源
        AtomicInteger index = new AtomicInteger(0);
        Map<Integer, String> secondDsMap = new HashMap<>(4);
        secondDsMap.put(index.incrementAndGet(), SECONDARY_DS);
        DataSourceHolder.setSecondaryDs(secondDsMap);
        return ds;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier(DYNAMIC_DS) DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH));
        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager testTransactionManager(
            @Qualifier(DYNAMIC_DS) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplateGeek(
            @Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
