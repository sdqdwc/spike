package com.mw.singleton;

/**
 * @author WangCH
 * @create 2018-03-14 22:45
 */
public class SingletonDemo2 {

    private static SingletonDemo2 instance;
    private SingletonDemo2(){

    }
    public static synchronized SingletonDemo2 getInstance(){
        if(instance==null){
            instance=new SingletonDemo2();
        }
        return instance;
    }
}
