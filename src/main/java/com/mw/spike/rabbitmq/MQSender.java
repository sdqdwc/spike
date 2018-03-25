package com.mw.spike.rabbitmq;

import com.mw.spike.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	AmqpTemplate amqpTemplate ;
	
	public void sendSpikeMessage(SpikeMessage mm) {
		String msg = RedisService.beanToString(mm);
		log.info("send message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.SPIKE_QUEUE, msg);
	}

	/**
	 * 测试send
	 * @param message
	 */
	public void send(Object message) {
		String msg = RedisService.beanToString(message);
		log.info("send message:" + msg);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
	}

	/**
	 * 测试topic模式
	 * @param message
	 */
/*	public void sendTopic(Object message) {
		String msg = RedisService.beanToString(message);
		log.info("send topic message:" + msg);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", msg+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg+"2");
	}*/

	/**
	 * 测试fanout模式
	 * @param message
	 */
/*	public void sendFanout(Object message) {
		String msg = RedisService.beanToString(message);
		log.info("send fanout message:" + msg);
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
	}*/

	/**
	 * 测试header模式
	 * @param message
	 */
/*	public void sendHeader(Object message) {
		String msg = RedisService.beanToString(message);
		log.info("send fanout message:"+msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");
		Message obj = new Message(msg.getBytes(), properties);
		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
	}*/

	
	
}
