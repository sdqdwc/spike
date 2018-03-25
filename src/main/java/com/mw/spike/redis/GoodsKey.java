package com.mw.spike.redis;

/**
 * @author WangCH
 * @create 2018-03-04 16:07
 */
public class GoodsKey extends BasePrefix{

    //过期时间
    public static final int TOKEN_EXPIRE = 10;

    private GoodsKey(int expireSeconds ,String prefix){
        super(expireSeconds,prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(TOKEN_EXPIRE,"gl");

    //static GoodsKey getGoodsDetail = new GoodsKey(TOKEN_EXPIRE,"gd");

    public static GoodsKey getSpikeGoodsStock = new GoodsKey(0,"gs");
}
