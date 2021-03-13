package com.github.oliverschen.dynamic.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ck
 */
@Getter
@AllArgsConstructor
public enum DsMethod {
    // todo 区分方法名进行分库分表
    INSERT,
    UPDATE,
    DELETE,
    NONE
}
