package com.github.oliverschen.controller;

import com.github.oliverschen.entity.Order;
import com.github.oliverschen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ck
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/insert")
    public String insert(@RequestBody Order order) {
        orderService.insert(order);
        return "OK";
    }

    @GetMapping("/get/{id}")
    public Order get(@PathVariable Long id) {
        return orderService.get(id);
    }

    @PostMapping("/update")
    public String update(@RequestBody Order order) {
        orderService.update(order);
        return "OK";
    }

    @DeleteMapping("/del/{id}")
    public String del(@PathVariable Long id) {
        orderService.del(id);
        return "OK";
    }

    @GetMapping("/stock/{doWork}")
    public String stock(@PathVariable String doWork) {
        orderService.stock(doWork);
        return "OK";
    }
}
