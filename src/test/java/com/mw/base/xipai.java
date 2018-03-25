package com.mw.base;

import com.mw.singleton.SingletonDemo;
import com.mw.singleton.SingletonDemo2;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WangCH
 * @create 2018-03-09 20:20
 */
public class xipai {

    private Lock accountLock = new ReentrantLock();

    public static void main(String[] args) {

        SingletonDemo2 singletonDemo2 = SingletonDemo2.getInstance();
        SingletonDemo singletonDemo = SingletonDemo.getInstance();
        ClassLoader sad =  ClassLoader.getSystemClassLoader();
        //sad.loadClass();
        //LinkedHashMap
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 20; i++)
            list.add(new Integer(i));
        //Iterator iterator = list.iterator();
        //while(iterator.hasNext()){
            //System.out.println(iterator.next());
        //}
        //Collections.
       // System.out.println("打乱前:");
       // System.out.println(list);
       // Collections.shuffle(list);
       // System.out.println(list);
        /*for (int i = 0; i < 5; i++) {
            System.out.println("第" + i + "次打乱：");
            Collections.shuffle(list);
            System.out.println(list);
        }*/

        /*Vector<Integer> vector = new Vector<>();
        vector.add(21);
        System.out.println(vector);*/



       // CopyOnWriteArraySet
       //CopyOnWriteArrayList

        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        linkedList.add(1);
        linkedList.add(2);
        ArrayList<Integer> arrayList = new ArrayList<>(linkedList);
        System.out.println(arrayList);

        ExecutorService service = Executors.newFixedThreadPool(100);
        //Arrays.sort();
        //Collections.

        //() -> Collections
    }
}
