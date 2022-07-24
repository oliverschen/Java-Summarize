package com.github.oliverschen.config;


import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Getter
@Setter
@Configuration
public class HbaseConfig {

    @Value("${hbase.zookeeper.quorum}")
    private String zk;


    /**
     * 创建 hbase 配置
     */
    public org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", zk);
        return configuration;
    }


    @Bean
    public Admin admin() {
        try {
            Connection connection = ConnectionFactory.createConnection(configuration());
            return connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
