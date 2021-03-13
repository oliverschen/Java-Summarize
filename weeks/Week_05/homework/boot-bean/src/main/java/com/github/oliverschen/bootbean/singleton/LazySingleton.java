package com.github.oliverschen.bootbean.singleton;

/**
 * @author ck
 * 懒汉式
 * 1. 私有化构造方法
 * 2. 提供公共方法，在获取时判断要不要创建对象，这里存在线程安全问题
 */
public class LazySingleton {

    private static LazySingleton INSTANCE = null;

    private LazySingleton() {
    }

    /**
     * 双重检测:存在问题
     * 1. 在第一个线程还没有完成创建好实例对象的时候，第二个线程可能会获取到一个不完整的对象，就会导致后续依赖当前实例的程序崩溃
     * 不推荐使用
     */
    public static LazySingleton getInstance() {
        // 防止每次调用时加锁增加开销
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                // 防止多线程情况下后面线程再次创建实例
                if (INSTANCE == null) {
                    INSTANCE = new LazySingleton();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * synchronized 性能优化之后，直接同步整个方法程序更加可靠
     */
    public static synchronized LazySingleton getInstanceSafe() {
        if (INSTANCE == null) {
            INSTANCE = new LazySingleton();
        }
        return INSTANCE;
    }
}
