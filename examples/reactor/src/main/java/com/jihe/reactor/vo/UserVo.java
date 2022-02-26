package com.jihe.reactor.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private String userName;

    private String mobile;

    private Integer age;
}
