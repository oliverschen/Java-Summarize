package com.github.oliverschen.service;


import com.github.oliverschen.entity.Order;

/**
 * @author ck
 */
public interface OrderService {

    void insert(Order order);

    Order get(Long id);

    void update(Order order);

    void del(Long id);

    void stock(String doWork);
}
