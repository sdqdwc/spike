package com.mw.spike.controller;

import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.RedisService;
import com.mw.spike.result.Result;
import com.mw.spike.service.SpikeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
    SpikeUserService userService;
	
	@Autowired
    RedisService redisService;
	
    @RequestMapping("/info")
    @ResponseBody
    public Result<SpikeUser> info(Model model, SpikeUser user) {
        return Result.success(user);
    }
    
}
