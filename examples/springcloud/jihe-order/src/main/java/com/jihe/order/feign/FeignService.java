package com.jihe.order.feign;

import com.jihe.order.feign.fallback.FeignServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description: feign 服务
 * @author: ck
 * @time: 2019-12-15 10:49
 **/
@FeignClient(value = "jihe-user",fallback = FeignServiceFallBack.class)
public interface FeignService {

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    String getUser(@PathVariable("id") int id);
}
