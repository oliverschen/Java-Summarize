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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author ck
 */
@Configuration
@MapperScan(basePackages = "com.github.oliverschen.mapper.user2",
            sqlSessionFactoryRef = "sqlSessionFactoryUser2",
            sqlSessionTemplateRef = "sqlSessionTemplateUser2")
public class DataSourceConfigUser2 {

    private static final String MAPPER_PATH = "classpath:mapper/user2/*.xml";

    @Primary
    @Bean(name = "dataSourceUser2")
    @ConfigurationProperties(prefix = "spring.datasource.user2")
    public DataSource datasourceUser2() {
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "sqlSessionFactoryUser2")
    public SqlSessionFactory sqlSessionFactoryUser2(
            @Qualifier("dataSourceUser2") DataSource datasourceUser2) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setDataSource(datasourceUser2);
        bean.setConfiguration(configuration);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_PATH));
        return bean.getObject();
    }


    @Primary
    @Bean(name = "transactionManagerUser2")
    public DataSourceTransactionManager User2TransactionManagerUser2(
            @Qualifier("dataSourceUser2") DataSource dataSourceUser2) {
        return new DataSourceTransactionManager(dataSourceUser2);
    }


    @Primary
    @Bean(name = "sqlSessionTemplateUser2")
    public SqlSessionTemplate sqlSessionTemplateUser2(
            @Qualifier("sqlSessionFactoryUser2") SqlSessionFactory sqlSessionFactoryUser2) {
        return new SqlSessionTemplate(sqlSessionFactoryUser2);
    }


}
