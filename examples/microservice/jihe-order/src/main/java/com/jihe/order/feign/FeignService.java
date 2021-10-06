package com.jihe.order.feign;

import com.jihe.order.feign.fallback.FeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description: feign 服务
 * @author: ck
 * @time: 2019-12-15 10:49
 **/
@FeignClient(value = "jihe-user",fallback = FeignServiceFallBack.class)
public interface FeignService {

    @GetMapping(value = "/user/{id}")
    String getUser(@PathVariable("id") int id);
}
