package com.mw.spike.rabbitmq;

import com.mw.spike.domain.SpikeOrder;
import com.mw.spike.domain.SpikeUser;
import com.mw.spike.redis.RedisService;
import com.mw.spike.service.GoodsService;
import com.mw.spike.service.OrderService;
import com.mw.spike.service.SpikeService;
import com.mw.spike.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MQReceiver {

	private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;

	@Autowired
	OrderService orderService;

	@Autowired
	SpikeService spikeService;

	@RabbitListener(queues=MQConfig.SPIKE_QUEUE)
	public void receive(String message) {
		log.info("receive message:"+message);
		SpikeMessage mm  = RedisService.stringToBean(message, SpikeMessage.class);
		SpikeUser user = mm.getUser();
		long goodsId = mm.getGoodsId();

		//判断库存
		GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
		int stock = goods.getStockCount();
		if(stock <= 0) {
			return;
		}
		//判断是否已经秒杀到了，Redis
		SpikeOrder order = orderService.getSpikeOrderByUserIdGoodsId(user.getId(), goodsId);
		if(order != null) {
			return;
		}
		//减库存 下订单 写入秒杀订单
		spikeService.spike(user, goods);
	}
	
	/*@RabbitListener(queues=MQConfig.QUEUE)
	public void receiveTest(String message) {
		log.info("receive message:"+message);
	}

	@RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
	public void receiveTopic1(String message) {
		log.info(" topic  queue1 message:"+message);
	}

	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
	public void receiveTopic2(String message) {
		log.info(" topic  queue2 message:"+message);
	}

	@RabbitListener(queues=MQConfig.HEADER_QUEUE)
	public void receiveHeaderQueue(byte[] message) {
		log.info(" header  queue message:"+new String(message));
	}*/

		
}
