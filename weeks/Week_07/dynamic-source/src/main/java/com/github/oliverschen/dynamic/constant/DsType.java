package com.github.oliverschen.dynamic.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ck
 */
@Getter
@AllArgsConstructor
public enum DsType {
    // 数据源类型
    PRIMARY("primaryDs"),
    SECONDARY("secondaryDs"),
    ;
    private final String name;
}
