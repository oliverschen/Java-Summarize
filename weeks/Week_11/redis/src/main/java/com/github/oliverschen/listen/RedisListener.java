package com.github.oliverschen.listen;

import com.github.oliverschen.common.JsonUtil;
import com.github.oliverschen.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author ck
 */
@Slf4j
@Component
public class RedisListener {


    public void process(String msg,String publishKey) throws IOException {
        Order order = JsonUtil.json2Object(msg, Order.class);
        log.info("publish key :{},order id :{} ===> process order info", publishKey, order.getId());
    }
}
