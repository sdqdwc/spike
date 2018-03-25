package com.mw.pattern.cglib;

import com.mw.pattern.RealSubject;
import com.mw.pattern.Subject;
import com.mw.pattern.dynamic.JdkProxySubject;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author WangCH
 * @create 2018-03-15 22:26
 */
public class Client {
    public static void main(String[] args) {
        //增强器，动态代码生成器
        Enhancer enhancer = new Enhancer();
        //设置生成类的父类类型
        enhancer.setSuperclass(RealSubject.class);
        //回调方法
        enhancer.setCallback(new DemoMethodInterceptor());
        //动态生成字节码并返回代理对象
        Subject subject = (Subject) enhancer.create();
        subject.hello();

    }
}
