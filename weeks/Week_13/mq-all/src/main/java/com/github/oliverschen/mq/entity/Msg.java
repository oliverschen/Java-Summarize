package com.github.oliverschen.mq.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ck
 */
@Data
public class Msg {
    private Long id;
    private String msg;
    private LocalDateTime sendTime;
}
