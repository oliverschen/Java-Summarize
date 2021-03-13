package com.github.oliverschen.springbean;

import org.springframework.stereotype.Service;

/**
 * @author ck
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

    @Override
    public void order() {
        System.out.println("下订单...");
    }
}
