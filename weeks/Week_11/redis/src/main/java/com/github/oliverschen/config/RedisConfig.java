package com.github.oliverschen.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.oliverschen.listen.RedisListener;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ck
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    public static final String REDIS_CHANNEL_ORDER = "REDIS_CHANNEL_ORDER";
    private static final String REDIS_LISTEN_METHOD = "process";

    /**
     * 自定义生成redis-key
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean
    public <T, V> RedisTemplate<T, V> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<T, V> redisTemplate = new RedisTemplate<>();
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<?> jackson = buildJacksonSerializer();
        redisTemplate.setConnectionFactory(factory);
        //key序列化方式
        redisTemplate.setKeySerializer(redisSerializer);
        //value序列化
        redisTemplate.setValueSerializer(jackson);
        //value hashmap序列化
        redisTemplate.setHashValueSerializer(jackson);
        redisTemplate.setHashKeySerializer(redisSerializer);
        return redisTemplate;
    }

    private Jackson2JsonRedisSerializer<?> buildJacksonSerializer() {
        Jackson2JsonRedisSerializer<?> jackson = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 注入字段类型「类信息」
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 碰到不能解析字段不报错
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁止时间字段解析成时间戳
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Java8 新时间日期类型支持
        om.registerModule(new JavaTimeModule());
        jackson.setObjectMapper(om);
        return jackson;
    }


    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = buildJacksonSerializer();
        // 配置序列化（解决乱码的问题）,过期时间3600秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }


    /**
     * 绑定监听方法
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisListener redisListener) {
        return new MessageListenerAdapter(redisListener, REDIS_LISTEN_METHOD);
    }

    /**
     * 订阅配置
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                                   MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listenerAdapter, PatternTopic.of(REDIS_CHANNEL_ORDER));
        return container;
    }



}
