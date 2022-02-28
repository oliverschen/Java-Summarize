package com.jihe.reactor.controller;


import com.jihe.reactor.service.ReactorService;
import com.jihe.reactor.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/web")
public class ReactorController {


    @Autowired
    private ReactorService reactorService;

    @GetMapping("/mono")
    public Mono<UserVo> getUser() {
        UserVo user = reactorService.getUser();
        return Mono.just(user);
    }



}

