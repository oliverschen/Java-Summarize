package com.github.oliverschen.dynamic.ss.mapper;

import com.github.oliverschen.dynamic.ss.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ck
 */
@Mapper
public interface OrderMapper {

    void insert(Order order);

    Order get(Long id);
}
