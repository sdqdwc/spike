package com.mw.spike.service;

import com.mw.spike.dao.SpikeUserDao;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.exception.GlobalException;
import com.mw.spike.redis.RedisService;
import com.mw.spike.redis.SpikeUserKey;
import com.mw.spike.result.CodeMsg;
import com.mw.spike.util.MD5Util;
import com.mw.spike.util.UUIDUtil;
import com.mw.spike.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @author WangCH
 * @create 2018-02-28 14:11
 */
@Service
public class SpikeUserService {

    public static final String COOKIE_NAME_TOKEN = "token";


    @Autowired
    private SpikeUserDao spikeUserDao;

    @Autowired
    private RedisService redisService;


    public SpikeUser getById(Long id){
        //取缓存
        SpikeUser user = redisService.get(SpikeUserKey.getById,""+id, SpikeUser.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = spikeUserDao.getById(id);
        if(user != null){
            redisService.set(SpikeUserKey.getById,""+id,user);
        }
        return user;
    }

    public boolean updatePassword(String token,long id,String formPass){
        //取user
        SpikeUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        SpikeUser toBeUpdate = new SpikeUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDbPass(formPass,user.getSalt()));
        spikeUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(SpikeUserKey.getById,"" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(SpikeUserKey.token,"" + token, user);
        return true;
    }

    /**
     * 登录判断
     * @param loginVo
     * @return
     */
    public String login(HttpServletResponse response, LoginVo loginVo){
        //判断传参是否正常
        if(loginVo == null){
           throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        SpikeUser user =  getById(Long.parseLong(mobile));
        if(user==null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calPass = MD5Util.formPassToDbPass(formPass,dbSalt);
        if(!calPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response,token,user);
        return token;
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    public SpikeUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        SpikeUser user = redisService.get(SpikeUserKey.token,token, SpikeUser.class);
        //延长有效期
        if(user !=null){
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     * 生成cookie
     * @param response
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, SpikeUser user){
        //redis缓存
        redisService.set(SpikeUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(SpikeUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);//浏览器响应
    }


}
