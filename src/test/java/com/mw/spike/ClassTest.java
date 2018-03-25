package com.mw.spike;

import com.mw.spike.domain.SpikeUser;
import org.junit.Test;

/**
 * @author WangCH
 * @create 2018-03-06 21:46
 */
public class ClassTest extends Object {

    @Test
    public void test1(){


        //Class类重写了toString()方法
        Class<?> test = ClassTest.class;
        System.out.println(test);
        System.out.println(ClassTest.class);

        SpikeUser user = new SpikeUser();
        System.out.println(user);
        System.out.println(SpikeUser.class);

        System.gc();

        //System.out.println(test.toGenericString()); //jdk 1.8
        //System.out.println(test.getSimpleName());
        //System.out.println(test.getName());
    }
}

class A {
    public void getA(){
        System.out.println("AAA");
    }
}
class B extends A{

}
class C extends B{

    @Override
    public void getA() {
        super.getA();
    }
}
