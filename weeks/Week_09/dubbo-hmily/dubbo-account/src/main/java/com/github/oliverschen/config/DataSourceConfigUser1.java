package com.github.oliverschen.config;


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

/**
 * @author ck
 */
@Configuration
@MapperScan(basePackages = "com.github.oliverschen.mapper.user1",
            sqlSessionFactoryRef = "sqlSessionFactoryUser1",
            sqlSessionTemplateRef = "sqlSessionTemplateUser1")
public class DataSourceConfigUser1 {

    private static final String MAPPER_PATH = "classpath:mapper/user1/*.xml";

    @Bean(name = "dataSourceUser1")
    @ConfigurationProperties(prefix = "spring.datasource.user1")
    public DataSource datasourceUser1() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "sqlSessionFactoryUser1")
    public SqlSessionFactory sqlSessionFactoryUser1(
            @Qualifier("dataSourceUser1") DataSource datasourceUser1,
            @Qualifier("configuration") org.apache.ibatis.session.Configuration configuration)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasourceUser1);
        bean.setConfiguration(configuration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH));
        return bean.getObject();
    }

    /**
     * 主要加载驼峰命名转换
     */
    @Bean(name = "configuration")
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean(name = "transactionManagerUser1")
    public DataSourceTransactionManager testTransactionManager(
            @Qualifier("dataSourceUser1") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateUser1")
    public SqlSessionTemplate sqlSessionTemplateUser1(@Qualifier("sqlSessionFactoryUser1")
                                                                SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
