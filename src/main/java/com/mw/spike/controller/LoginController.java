package com.mw.spike.controller;

import com.mw.spike.redis.RedisService;
import com.mw.spike.result.CodeMsg;
import com.mw.spike.result.Result;
import com.mw.spike.service.SpikeUserService;
import com.mw.spike.util.ValidatorUtil;
import com.mw.spike.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author WangCH
 * @create 2018-02-28 11:20
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SpikeUserService spikeUserService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> do_login(HttpServletResponse response, @Valid LoginVo loginVo){
        //log.info(loginVo.toString());
        //登录
        String token = spikeUserService.login(response,loginVo);
        return Result.success(token);
    }

}
