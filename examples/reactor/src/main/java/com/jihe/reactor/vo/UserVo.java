package com.jihe.reactor.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo {

    private String userName;

    private String mobile;

    private Integer age;
}
