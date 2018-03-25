package com.mw.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-09 19:42
 */
public class TestArrayCollection {

    public static void main(String[] args) {

        List<String> strings = Arrays.asList("6", "1", "3", "1","2");

        Collections.sort(strings);//sort方法在这里

        for (String string : strings) {

            System.out.println(string);
        }
    }
}
