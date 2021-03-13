package com.github.oliverschen.dynamic.ss.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ck
 */
@Data
public class Order {

    private Integer orderId;
    private Integer userId;
    private String desc;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
