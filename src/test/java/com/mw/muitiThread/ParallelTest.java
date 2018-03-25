package com.mw.muitiThread;

import com.alibaba.fastjson.JSONArray;
import com.mw.spike.domain.SpikeOrder;
import com.mw.utils.CommonUtil;
import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author WangCH
 * @create 2018-03-08 18:45
 */
public class ParallelTest {

    private CommonUtil commonUti ;

    /**
     * 测试多线程请求
     */
    @Test
    public void testGetCallable(){
        Long begin = new Date().getTime();
        List<SpikeOrder> result = new LinkedList<>();
        List<Future> resultList = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for(int i =0 ;i<20000;i=i+10){
            String url = "http://127.0.0.1:8080/test/getOrder/"+i;
            HttpGetCallable httpGetCallable = new HttpGetCallable(url);
            Future future = executorService.submit(httpGetCallable);
            resultList.add(future);
        }
        System.out.println("dispatched successfully");

        executorService.shutdown();//不再接受新任务

        while (true) {
            if (executorService.isTerminated()) {
                //所有任务完成后跳出这个检测循环
                for (int i = 0; i < resultList.size(); i++) {
                    Future<List<SpikeOrder>> spikeList = resultList.get(i);
                    List<SpikeOrder> orders = null;
                    try {
                        orders = spikeList.get();// blocking method
                        result.addAll(orders);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
        System.out.println(result);
        System.out.println(result.size());
        Long end = new Date().getTime();
        System.out.println("cast : " + (end - begin) + " ms");
    }


    /**
     * 测试循环请求
     */
    @Test
    public void testGetFor(){
        Long begin = new Date().getTime();
        List<SpikeOrder> result = new LinkedList<>();
        for(int i =0 ;i<10000;i=i+10){
            String url = "http://127.0.0.1:8080/test/getOrder/"+i;
            JSONArray jsonArray = commonUti.httpRequest(url, "GET");
            result.addAll(jsonArray.toJavaList(SpikeOrder.class));
        }
        System.out.println(result.size());
        Long end = new Date().getTime();
        System.out.println("cast : " + (end - begin) + " ms");
    }




    /**
     * 多线程测试,buzhongyao
     */
    @Test
    public void test(){
        List<Future> resultList = new LinkedList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<20;i++){
            MultiTest multiTest = new MultiTest(i);
            Future future = executorService.submit(multiTest);
            resultList.add(future);
        }
        System.out.println("dispatched successfully");

        executorService.shutdown();//不在接受新任务

        while (true) {
            if (executorService.isTerminated()) {
                //所有任务完成后跳出这个检测循环
                for (int i = 0; i < resultList.size(); i++) {
                    Future<Integer> result = resultList.get(i);
                    Integer number = null;
                    try {
                        number = result.get();// blocking method
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    System.out.printf("task: %d, result %d:\n", i, number);
                }
                //System.out.println(resultList);
                break;
            }
        }
    }
}
