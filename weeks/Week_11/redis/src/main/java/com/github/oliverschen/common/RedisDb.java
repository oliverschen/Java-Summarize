package com.github.oliverschen.common;

import com.github.oliverschen.exception.DbEmptyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 模拟减库存
 * @author ck
 */
@Component
public class RedisDb<K,V> {

    @Resource
    private RedisTemplate<K, V> redisTemplate;

    private static final Long SUCCESS = 1L;
    private static final Long FAIL = 0L;
    private static final String DECREMENT_SCRIPT = "if redis.call('decrby', KEYS[1], ARGV[1]) < 0 then redis.call('incrby', KEYS[1], ARGV[1]) return 0 else return 1 end";


    /**
     * 增加库存
     *
     * @param count 数量
     * @return 剩余数量
     */
    public Long increment(K k, Long count) {
        if (count <= 0L) {
            return (Long) redisTemplate.opsForValue().get(k);
        }
        return redisTemplate.opsForValue().increment(k, count);
    }


    /**
     * 减少库存
     * @param k key
     * @param count 减少数量
     * @return 是否成功扣减
     */
    public boolean decrement(K k, Integer count) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(DECREMENT_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script, Collections.singletonList(k), count);
        if (SUCCESS.equals(result)) {
            return true;
        }
        if (FAIL.equals(result)) {
            throw new DbEmptyException("库存不足");
        }
        return false;
    }

}
