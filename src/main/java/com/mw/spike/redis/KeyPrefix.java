package com.mw.spike.redis;

/**
 * @author WangCH
 * @create 2018-02-27 18:11
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
