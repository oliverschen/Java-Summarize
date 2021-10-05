package com.jihe.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: user
 * @author: ck
 * @time: 2019-12-14 23:19
 **/
@Api("用户系统-API")
@RestController
public class UserController {

    @Value("${server.port}")
    String port;


    @ApiOperation(value = "返回用户服务信息",notes = "根据 ID 返回用户和服务信息")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType = "Integer")
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") int id) {
        return "我是" + id + "号用户" + ",访问端口：" + port;
    }

}
