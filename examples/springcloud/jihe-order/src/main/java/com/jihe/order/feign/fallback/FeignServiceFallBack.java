package com.jihe.order.feign.fallback;

import com.jihe.order.feign.FeignService;
import org.springframework.stereotype.Component;

@Component
public class FeignServiceFallBack implements FeignService {
    @Override
    public String getUser(int id) {
        return "user service is busy,please wait";
    }
}
