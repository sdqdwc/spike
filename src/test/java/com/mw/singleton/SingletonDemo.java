package com.mw.singleton;

/**
 * 饿汉模式
 * @author WangCH
 * @create 2018-03-14 22:39
 */
public class SingletonDemo {

    private static SingletonDemo instance = new SingletonDemo();

    private SingletonDemo(){

    }

    public static SingletonDemo getInstance(){
        return instance;
    }

}
