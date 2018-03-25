package com.mw.pattern;

/**
 * @author WangCH
 * @create 2018-03-15 21:17
 */
public class RealSubject implements Subject {

    @Override
    public void request() {
        System.out.println("real subject execute request");
    }

    @Override
    public void hello() {
        System.out.println("hello hello hello !!!");
    }
}
