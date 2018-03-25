package com.mw.spike.controller;

import com.mw.spike.access.AccessLimit;
import com.mw.spike.domain.OrderInfo;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.RedisService;
import com.mw.spike.result.CodeMsg;
import com.mw.spike.result.Result;
import com.mw.spike.service.GoodsService;
import com.mw.spike.service.OrderService;
import com.mw.spike.service.SpikeUserService;
import com.mw.spike.vo.GoodsVo;
import com.mw.spike.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	SpikeUserService userService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/detail")
    @ResponseBody
	//@AccessLimit()
    public Result<OrderDetailVo> info(SpikeUser user, @RequestParam("orderId") long orderId) {
    	if(user == null) {
    		return Result.error(CodeMsg.SESSION_ERROR);
    	}
    	OrderInfo order = orderService.getOrderById(orderId);
    	if(order == null) {
    		return Result.error(CodeMsg.ORDER_NOT_EXIST);
    	}
    	long goodsId = order.getGoodsId();
    	GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
    	OrderDetailVo vo = new OrderDetailVo();
    	vo.setOrder(order);
    	vo.setGoods(goods);
    	return Result.success(vo);
    }


    
}
