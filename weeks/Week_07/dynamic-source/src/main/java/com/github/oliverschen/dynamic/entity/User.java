package com.github.oliverschen.dynamic.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author ck
 */
@Data
public class User {

    private Long id;
    private String username;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birth;
}
