package com.mw.spike.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author WangCH
 * @create 2018-02-28 10:57
 */
public class MD5Util {

    //md5
    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    //固定salt
    private static final String salt = "ui2ov5xzx4c";

    //第一次md5
    public static String inputPassToFormPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(1) + inputPass + salt.charAt(3) + salt.charAt(4);
        return md5(str);
    }

    //第二次md5
    public static String formPassToDbPass(String formPass,String salt){
        String str = "" + salt.charAt(1) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(6);
        return md5(str);
    }

    //测试2次md5加密
    public static String inputPassToDbPass(String inputPass,String saltDB){
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDbPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args){
        //3bb1f851d81504bfcd2092ab5fe897aa
        System.out.println(inputPassToFormPass("123456"));//3bb1f851d81504bfcd2092ab5fe897aa
        //System.out.println(inputPassToDbPass("123456","xjkcvhkjxh"));
    }


}
