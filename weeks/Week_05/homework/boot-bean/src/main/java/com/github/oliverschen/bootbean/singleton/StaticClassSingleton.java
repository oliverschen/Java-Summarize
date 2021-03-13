package com.github.oliverschen.bootbean.singleton;


/**
 * @author ck
 * 静态内部类:推荐
 */
public class StaticClassSingleton {

    private StaticClassSingleton() {
    }

    private static class Holder{
        private static final StaticClassSingleton INSTANCE = new StaticClassSingleton();
    }


    public static StaticClassSingleton getInstance() {
        return Holder.INSTANCE;
    }
}











