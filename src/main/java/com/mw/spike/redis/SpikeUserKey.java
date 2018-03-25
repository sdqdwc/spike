package com.mw.spike.redis;

/**
 * @author WangCH
 * @create 2018-02-27 18:18
 */
public class SpikeUserKey extends BasePrefix {

    //过期时间
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private SpikeUserKey(int expireSeconds, String prefix){
        super(expireSeconds,prefix);
    }

    public static SpikeUserKey token = new SpikeUserKey(TOKEN_EXPIRE,"tk");

    public static SpikeUserKey getById = new SpikeUserKey(0,"id");
}
