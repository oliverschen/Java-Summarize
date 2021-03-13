package com.github.oliverschen.dynamic.annotation;

import com.github.oliverschen.dynamic.constant.DsType;
import com.github.oliverschen.dynamic.constant.DsMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ck
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Ds {

    /**
     * 指定操作的目标方法-待实现
     */
    DsMethod dsMethod() default DsMethod.NONE;

    /**
     * 指定当前数据源-默认从库
     */
    DsType dsType() default DsType.SECONDARY;
}
