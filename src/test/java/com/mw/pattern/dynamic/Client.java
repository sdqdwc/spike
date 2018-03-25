package com.mw.pattern.dynamic;

import com.mw.pattern.RealSubject;
import com.mw.pattern.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author WangCH
 * @create 2018-03-15 22:26
 */
public class Client {
    public static void main(String[] args) {
        //代理的真实对象
        RealSubject realSubject = new RealSubject();
        /**
         * InvocationHandlerImpl 实现了 InvocationHandler 接口，并能实现方法调用从代理类到委托类的分派转发
         * 其内部通常包含指向委托类实例的引用，用于真正执行分派转发过来的方法调用.
         * 即：要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法
         */
        InvocationHandler handler = new JdkProxySubject(realSubject);

        ClassLoader loader = realSubject.getClass().getClassLoader();
        Class[] interfaces = realSubject.getClass().getInterfaces();
        Subject subject = (Subject) Proxy.newProxyInstance(loader, interfaces, handler);
        //Subject subject = (Subject) Proxy.newProxyInstance(Client.class.getClassLoader(),
        //        new Class[]{Subject.class},new JdkProxySubject(new RealSubject()));
        subject.hello();
        subject.request();
    }
}
