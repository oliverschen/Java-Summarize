package com.github.oliverschen.dynamic.ss.service;

import com.github.oliverschen.dynamic.ss.entity.Order;

/**
 * @author ck
 */
public interface OrderService {
    void insert(Order order);

    Order get(Long id);
}
