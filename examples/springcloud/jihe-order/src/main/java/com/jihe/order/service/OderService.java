package com.jihe.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @description: service
 * @author: ck
 * @time: 2019-12-14 23:29
 **/
@Service
public class OderService {
    @Autowired
    private RestTemplate restTemplate;

    public String getUser(int id) {
        String url = "http://jihe-user/user/{id}";
        return restTemplate.getForObject(url, String.class, id);
    }
}
