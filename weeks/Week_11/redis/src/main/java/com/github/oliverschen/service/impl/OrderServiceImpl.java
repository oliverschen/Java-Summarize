package com.github.oliverschen.service.impl;

import com.github.oliverschen.common.RedisDb;
import com.github.oliverschen.common.RedisLock;
import com.github.oliverschen.entity.Order;
import com.github.oliverschen.mapper.OrderMapper;
import com.github.oliverschen.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static com.github.oliverschen.config.RedisConfig.REDIS_CHANNEL_ORDER;

/**
 * @author ck
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private RedisLock<String,Object> redisLock;
    @Autowired
    private RedisDb<String, Object> redisDb;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void insert(Order order) {
        LocalDateTime now = LocalDateTime.now();
        order.setCreateTime(now);
        order.setUpdateTime(now);
        orderMapper.insert(order);
        // 发布订阅消息
        log.info("publish msg order id : {}", order.getId());
        redisTemplate.convertAndSend(REDIS_CHANNEL_ORDER, order);
    }

    @Override
    @Cacheable(key = "#id",value = "userCache")
    public Order get(Long id) {
        log.info("get in db ==> {}", id);
        return orderMapper.get(id);
    }

    @Override
    @CachePut(key = "#order.id",value = "userCache")
    public void update(Order order) {
        boolean result = redisLock.lock("chenkui", 10L, 10L);
        if (result) {
            orderMapper.update(order);
        }
        redisLock.unlock();
    }

    @Override
    @CacheEvict(key = "#id",value = "userCache")
    public void del(Long id) {
        orderMapper.del(id);
    }

    @Override
    public void stock(String doWork) {
        if ("add".equals(doWork)) {
            redisDb.increment("zzz", 1L);
        }else {
            redisDb.decrement("zzz", 1);
        }
    }

}
