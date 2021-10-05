package com.jihe.order.controller;

import com.jihe.order.feign.FeignService;
import com.jihe.order.service.OderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: order
 * @author: ck
 * @time: 2019-12-14 23:28
 **/
@RestController
public class OderController {

    @Autowired
    private OderService oderService;

    @RequestMapping("/order/{name}/{id}")
    public String getOrderAndUserInfo(@PathVariable("name") String name, @PathVariable("id") int id) {
        String user = oderService.getUser(id);
        return "我是：" + name + "," + user;
    }

    @Resource
    private FeignService feignService;

    @RequestMapping("/order-feign/{name}/{id}")
    public String getOrderAndUserInfoByFeign(@PathVariable("name") String name, @PathVariable("id") int id) {
        String user = feignService.getUser(id);
        return "FEIGN-我是：" + name + "," + user;
    }
}
