package com.mw.spike.rabbitmq;


import com.mw.spike.domain.SpikeUser;

public class SpikeMessage {
	private SpikeUser user;
	private long goodsId;
	public SpikeUser getUser() {
		return user;
	}
	public void setUser(SpikeUser user) {
		this.user = user;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
}
