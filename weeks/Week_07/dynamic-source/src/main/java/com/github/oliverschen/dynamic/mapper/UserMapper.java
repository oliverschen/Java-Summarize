package com.github.oliverschen.dynamic.mapper;


import com.github.oliverschen.dynamic.entity.User;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author ck
 */
@Mapper
public interface UserMapper {

    void insert(User user);

    User get(Long id);
}
