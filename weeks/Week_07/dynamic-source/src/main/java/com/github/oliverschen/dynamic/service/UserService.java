package com.github.oliverschen.dynamic.service;
import com.github.oliverschen.dynamic.entity.User;

/**
 * @author ck
 */
public interface UserService {

    void insert(User user);

    User get(Long id);
}
