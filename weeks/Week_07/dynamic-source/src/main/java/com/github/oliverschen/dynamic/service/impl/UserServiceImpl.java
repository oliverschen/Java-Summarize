package com.github.oliverschen.dynamic.service.impl;

import com.github.oliverschen.dynamic.annotation.Ds;
import com.github.oliverschen.dynamic.constant.DsType;
import com.github.oliverschen.dynamic.entity.User;
import com.github.oliverschen.dynamic.mapper.UserMapper;
import com.github.oliverschen.dynamic.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ck
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    @Ds(dsType = DsType.PRIMARY)
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    @Ds(dsType = DsType.SECONDARY)
    public User get(Long id) {
        return userMapper.get(id);
    }

}
