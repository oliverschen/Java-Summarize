package com.github.oliverschen.bootbean.singleton;

/**
 * @author ck
 * 饿汉式
 * 1. 私有化构造方法
 * 2. 在类加载初始化成员变量时创建
 * 3. 提供获取实例的方法
 */
public class HungrySingleton {

    private static final HungrySingleton INSTANCE = new HungrySingleton();

    private HungrySingleton() { }

    /**
     * 获取单例
     */
    public static HungrySingleton getInstance() {
        return INSTANCE;
    }

}
