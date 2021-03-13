package com.github.oliverschen.mapper;

import com.github.oliverschen.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ck
 */
@Mapper
public interface OrderMapper {

    Integer insert(Order order);

    Order get(Long id);

    void update(Order order);

    void del(Long id);

}
