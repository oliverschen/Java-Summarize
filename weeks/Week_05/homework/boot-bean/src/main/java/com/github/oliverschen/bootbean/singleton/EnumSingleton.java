package com.github.oliverschen.bootbean.singleton;

/**
 * @author ck
 * 枚举方式
 */
public enum  EnumSingleton {
    // 实例
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
