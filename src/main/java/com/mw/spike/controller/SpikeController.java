package com.mw.spike.controller;

import com.mw.spike.access.AccessLimit;
import com.mw.spike.domain.OrderInfo;
import com.mw.spike.domain.SpikeOrder;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.rabbitmq.MQSender;
import com.mw.spike.rabbitmq.SpikeMessage;
import com.mw.spike.redis.GoodsKey;
import com.mw.spike.redis.OrderKey;
import com.mw.spike.redis.RedisService;
import com.mw.spike.redis.SpikeKey;
import com.mw.spike.result.CodeMsg;
import com.mw.spike.result.Result;
import com.mw.spike.service.GoodsService;
import com.mw.spike.service.OrderService;
import com.mw.spike.service.SpikeService;
import com.mw.spike.service.SpikeUserService;
import com.mw.spike.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/spike")
public class SpikeController implements InitializingBean {

	@Autowired
	SpikeUserService userService;

	@Autowired
	RedisService redisService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	SpikeService spikeService;

	@Autowired
	MQSender mqSender;

	//本地标识，记录
	//private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();
	private ConcurrentHashMap<Long, Boolean> localOverMap =  new ConcurrentHashMap<Long, Boolean>();

	/**
	 * 系统初始化,InitializingBean接口
	 */
	public void afterPropertiesSet(){
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		if(goodsList == null){
			return ;
		}
		for(GoodsVo goods : goodsList){
			redisService.set(GoodsKey.getSpikeGoodsStock,"" + goods.getId(),goods.getStockCount());
			localOverMap.put(goods.getId(), false);
		}
	}

	/**
	 * 妙杀方法
	 * 出现超卖现象，服务器QPS: 70(1000 * 10并发),  / 本机QPS: 880(1000 * 10并发)
	 * 解决超卖问题，优化静态资源，增加redis缓存，本机QPS：1200(1000 * 10并发)
     * rabbitMQ + redis预减库存 + 本地标识，服务器QPS: 587~657(1000 * 10并发), / 本机QPS: 1800~2200(1000 * 10并发)
	 * @param user
	 * @param goodsId
	 * @param model
	 * @param path
	 * @return
	 */
    @RequestMapping(value = "/{path}/do_spike", method = RequestMethod.POST)
	@ResponseBody
	@AccessLimit(seconds = 5, maxCount = 5 )
	public Result<Integer> doSpike(Model model,SpikeUser user,
								   @RequestParam("goodsId")long goodsId,
								   @PathVariable("path")String path) {
    	model.addAttribute("user", user);
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
		//验证path
		boolean check = spikeService.checkPath(user, goodsId, path);
		if(!check){
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}

    	//本地标识判断是否秒杀结束
    	boolean over = localOverMap.get(goodsId);
    	if(over){
    		return Result.error(CodeMsg.SPIKE_OVER);
		}
    	//预减库存
    	long stock = redisService.decr(GoodsKey.getSpikeGoodsStock,""+goodsId);
		if(stock < 0){
			localOverMap.put(goodsId, true);
			return Result.error(CodeMsg.SPIKE_OVER);
		}
		//判断是否已经秒杀到了,redis缓存里查
		SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return Result.error(CodeMsg.REPEATE_SPIKE);
		}
		//入队
		SpikeMessage mm = new SpikeMessage();
		mm.setUser(user);
		mm.setGoodsId(goodsId);
		mqSender.sendSpikeMessage(mm);
		return Result.success(0);//排队中
    }


	/**
	 * orderId：成功
	 * -1：秒杀失败
	 * 0： 排队中
	 * */
	@RequestMapping(value="/result", method=RequestMethod.GET)
	@ResponseBody
	public Result<Long> spikeResult(Model model,SpikeUser user,
									  @RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
		if(user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}
		long result = spikeService.getSpikeResult(user.getId(), goodsId);
		return Result.success(result);
	}


	/**
	 * 请求秒杀地址
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@AccessLimit(seconds=5, maxCount=5,needLogin = true)
	@RequestMapping(value="/path", method=RequestMethod.GET)
	@ResponseBody
	public Result<String> getSpikePath(SpikeUser user, @RequestParam("goodsId")long goodsId) {
		/*@RequestParam(value="verifyCode", defaultValue="0")int verifyCode*/

		/*if(user == null) {
			return Result.error(CodeMsg.SESSION_ERROR);
		}*/
		/*boolean check = spikeService.checkVerifyCode(user, goodsId, verifyCode);
		if(!check) {
			return Result.error(CodeMsg.REQUEST_ILLEGAL);
		}*/
		String path = spikeService.createSpikePath(user, goodsId);
		return Result.success(path);
	}




    /**
     * 重置数据库数据，测试用
     * @return
     */
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset() {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getSpikeGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getSpikeOrderByUidGid);
        redisService.delete(SpikeKey.isGoodsOver);
        spikeService.reset(goodsList);
        return Result.success(true);
    }


}
