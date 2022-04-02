package com.loet.mine.test;

/**
 * 描述：Singleton
 *
 * @author 罗锅
 * @date 2021/9/28 11:09
 */
public class Singleton {
    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
