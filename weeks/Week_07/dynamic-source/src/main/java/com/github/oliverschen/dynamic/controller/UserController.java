package com.github.oliverschen.dynamic.controller;

import com.github.oliverschen.dynamic.entity.User;
import com.github.oliverschen.dynamic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ck
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public String insert(@RequestBody User user) {
        userService.insert(user);
        return "OK";
    }

    @GetMapping("/get/{id}")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }
}
