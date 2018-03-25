package com.mw.spike;

import com.mw.spike.MainApplication;
import com.mw.spike.redis.RedisService;
import com.mw.spike.redis.SpikeUserKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author WangCH
 * @create 2018-02-28 16:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class RedisTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void redisSet() throws Exception{
        redisService.set(SpikeUserKey.token, "1" , "测试key，老王真牛逼2");//UserKey_tk_1
    }
    @Test
    public void redisGet() throws Exception{
        String IdValue = redisService.get(SpikeUserKey.token, "1", String.class);
        System.out.println(IdValue);
    }


}
