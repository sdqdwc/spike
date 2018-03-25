package com.mw.spike.service;

import java.util.Date;

import com.mw.spike.dao.OrderDao;
import com.mw.spike.domain.OrderInfo;
import com.mw.spike.domain.SpikeOrder;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.OrderKey;
import com.mw.spike.redis.RedisService;
import com.mw.spike.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;

	@Autowired
	RedisService redisService;

	public SpikeOrder getSpikeOrderByUserIdGoodsId(long userId, long goodsId) {
		//return orderDao.getSpikeOrderByUserIdGoodsId(userId, goodsId);
		return redisService.get(OrderKey.getSpikeOrderByUidGid, "_"+userId+"_"+goodsId, SpikeOrder.class);
	}

	public OrderInfo getOrderById(long orderId) {
		return orderDao.getOrderById(orderId);
	}

	/**
	 * 生成订单
	 * @param user
	 * @param goods
	 * @return
	 */
	@Transactional
	public OrderInfo createOrder(SpikeUser user, GoodsVo goods) {
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getSpikePrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderDao.insert(orderInfo);

		SpikeOrder spikeOrder = new SpikeOrder();
		spikeOrder.setGoodsId(goods.getId());
		spikeOrder.setOrderId(orderInfo.getId());
		spikeOrder.setUserId(user.getId());
		orderDao.insertSpikeOrder(spikeOrder);
		redisService.set(OrderKey.getSpikeOrderByUidGid, "_"+user.getId()+"_"+goods.getId(), spikeOrder);
		return orderInfo;
	}

	public void deleteOrders() {
		orderDao.deleteOrders();
		orderDao.deleteSpikeOrders();
	}
	
}
