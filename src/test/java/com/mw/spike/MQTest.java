package com.mw.spike;

import com.mw.spike.MainApplication;
import com.mw.spike.rabbitmq.MQSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author WangCH
 * @create 2018-03-06 13:16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class MQTest {

    @Autowired
    MQSender mqSender;

    @Test
    public void mq(){
        mqSender.send("test,rabbitmq,hi!");
        System.out.println("Test Finish");
    }

    /*@Test
    public void mqTopic(){
        mqSender.sendTopic("Hello，我是老王！");
        System.out.println("Test Finish");
    }

    @Test
    public void mqFanout(){
        mqSender.sendFanout("老王还是厉害！");
        System.out.println("Test Finish");
    }

    @Test
    public void myHeader(){
        mqSender.sendHeader("老王为什么如此厉害？");
        System.out.println("Test Finish");
    }*/

}

