package com.mw.spike.util;

import java.util.UUID;

/**
 * @author WangCH
 * @create 2018-02-28 16:56
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
