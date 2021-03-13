package com.github.oliverschen.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson 配置
 * @author ck
 */
//@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redisson(RedisProperties properties) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                .setPassword(properties.getPassword());
        return (Redisson) Redisson.create(config);
    }
}
