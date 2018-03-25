package com.mw.spike.redis;

public class SpikeKey extends BasePrefix{

	private SpikeKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}
	public static SpikeKey isGoodsOver = new SpikeKey(0,"go");
	public static SpikeKey getSpikePath = new SpikeKey(60, "mp");
	public static SpikeKey getSpikeVerifyCode = new SpikeKey(300, "vc");
}
