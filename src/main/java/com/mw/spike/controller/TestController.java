package com.mw.spike.controller;

import com.mw.spike.domain.SpikeOrder;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.RedisService;
import com.mw.spike.result.Result;
import com.mw.spike.service.SpikeUserService;
import com.mw.spike.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-08 18:30
 */
@RestController
@RequestMapping(value="/test")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping(value="/getOrder/{start}", method = RequestMethod.GET, produces = "application/json")
    public List<SpikeOrder> getOrder(@PathVariable int start) {
        return testService.getSpikeOrderTest(start);
    }


}
