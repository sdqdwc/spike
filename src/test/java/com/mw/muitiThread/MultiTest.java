package com.mw.muitiThread;

import java.util.concurrent.*;

/**
 * @author WangCH
 * @create 2018-03-13 21:29
 */
public class MultiTest implements Callable{

    private Integer number;

    public MultiTest(Integer number){
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {

        System.out.println("测试多线程"+number);
        return number;
        //Random ra =new Random();
        //return ra.nextInt(1000);
    }


}
