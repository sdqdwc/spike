package com.mw.spike.service;

import com.mw.spike.domain.OrderInfo;
import com.mw.spike.domain.SpikeOrder;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.RedisService;
import com.mw.spike.redis.SpikeKey;
import com.mw.spike.util.MD5Util;
import com.mw.spike.util.UUIDUtil;
import com.mw.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;


@Service
public class SpikeService {
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;

	@Autowired
	RedisService redisService;

	@Transactional
	public OrderInfo spike(SpikeUser user, GoodsVo goods) {

		//减库存 下订单 写入秒杀订单
		boolean success = goodsService.reduceStock(goods);
		if(success) {
			//order_info spike_order
			return orderService.createOrder(user, goods);
		}else {
			setGoodsOver(goods.getId());
			return null;
		}
	}

	/**
	 * redis设置商品已经抢购完
	 * @param goodsId
	 */
	private void setGoodsOver(Long goodsId) {
		redisService.set(SpikeKey.isGoodsOver, ""+goodsId, true);
	}

	/**
	 * 判断商品是否已经抢购完
	 * @param goodsId
	 * @return
	 */
	private boolean getGoodsOver(long goodsId) {
		return redisService.exists(SpikeKey.isGoodsOver,""+goodsId);
	}

	/**
	 * 重置库存
	 * @param goodsList
	 */
	public void reset(List<GoodsVo> goodsList) {
		goodsService.resetStock(goodsList);
		orderService.deleteOrders();
	}

	/**
	 * 查询秒杀结果
	 * @param userId
	 * @param goodsId
	 * @return
	 */
	public long getSpikeResult(Long userId, long goodsId) {
		SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(userId, goodsId);
		if(order != null) {//秒杀成功
			return order.getOrderId();
		}else {
			boolean isOver = getGoodsOver(goodsId);
			if(isOver) {
				return -1;
			}else {
				return 0;
			}
		}
	}

	/**
	 * 验证请求地址
	 * @param user
	 * @param goodsId
	 * @param path
	 * @return
	 */
	public boolean checkPath(SpikeUser user, long goodsId, String path) {
		if(user == null || path == null) {
			return false;
		}
		String pathOld = redisService.get(SpikeKey.getSpikePath, ""+user.getId() + "_"+ goodsId, String.class);
		return path.equals(pathOld);
	}

	/**
	 * 创建请求地址Path
	 * @param user
	 * @param goodsId
	 * @return
	 */
	public String createSpikePath(SpikeUser user, long goodsId) {
		if(user == null || goodsId <=0) {
			return null;
		}
		String str = MD5Util.md5(UUIDUtil.uuid()+"123456");
		redisService.set(SpikeKey.getSpikePath, ""+user.getId() + "_"+ goodsId, str);
		return str;
	}






	public BufferedImage createVerifyCode(SpikeUser user, long goodsId) {
		if(user == null || goodsId <=0) {
			return null;
		}
		int width = 80;
		int height = 32;
		//create the image
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		// set the background color
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// draw the border
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// create a random instance to generate the codes
		Random rdm = new Random();
		// make some confusion
		for (int i = 0; i < 50; i++) {
			int x = rdm.nextInt(width);
			int y = rdm.nextInt(height);
			g.drawOval(x, y, 0, 0);
		}
		// generate a random code
		String verifyCode = generateVerifyCode(rdm);
		g.setColor(new Color(0, 100, 0));
		g.setFont(new Font("Candara", Font.BOLD, 24));
		g.drawString(verifyCode, 8, 24);
		g.dispose();
		//把验证码存到redis中
		int rnd = calc(verifyCode);
		redisService.set(SpikeKey.getSpikeVerifyCode, user.getId()+","+goodsId, rnd);
		//输出图片
		return image;
	}

	public boolean checkVerifyCode(SpikeUser user, long goodsId, int verifyCode) {
		if(user == null || goodsId <=0) {
			return false;
		}
		Integer codeOld = redisService.get(SpikeKey.getSpikeVerifyCode, user.getId()+","+goodsId, Integer.class);
		if(codeOld == null || codeOld - verifyCode != 0 ) {
			return false;
		}
		redisService.delete(SpikeKey.getSpikeVerifyCode, user.getId()+","+goodsId);
		return true;
	}

	private static int calc(String exp) {
		try {
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("JavaScript");
			return (Integer)engine.eval(exp);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static char[] ops = new char[] {'+', '-', '*'};
	/**
	 * + - *
	 * */
	private String generateVerifyCode(Random rdm) {
		int num1 = rdm.nextInt(10);
		int num2 = rdm.nextInt(10);
		int num3 = rdm.nextInt(10);
		char op1 = ops[rdm.nextInt(3)];
		char op2 = ops[rdm.nextInt(3)];
		String exp = ""+ num1 + op1 + num2 + op2 + num3;
		return exp;
	}

}
