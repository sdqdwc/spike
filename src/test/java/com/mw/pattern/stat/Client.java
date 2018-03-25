package com.mw.pattern.stat;

import com.mw.pattern.RealSubject;
import com.mw.pattern.Subject;

/**
 * @author WangCH
 * @create 2018-03-15 21:20
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new Proxy(new RealSubject());
        subject.request();
    }
}
