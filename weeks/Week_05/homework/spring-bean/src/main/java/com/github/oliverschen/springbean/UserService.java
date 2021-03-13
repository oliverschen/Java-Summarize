package com.github.oliverschen.springbean;

import org.springframework.stereotype.Service;

/**
 * @author ck
 */
@Service
public class UserService {

    public void getUser() {
        System.out.println("当前用户是李四。。。");
    }
}
