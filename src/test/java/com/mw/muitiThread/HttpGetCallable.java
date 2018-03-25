package com.mw.muitiThread;

import com.alibaba.fastjson.JSONArray;
import com.mw.spike.domain.SpikeOrder;
import com.mw.utils.CommonUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author WangCH
 * @create 2018-03-13 22:59
 */
public class HttpGetCallable implements Callable {

    private CommonUtil commonUti ;

    private String url;

    public HttpGetCallable(String url){
        this.url = url;
    }

    @Override
    public List<SpikeOrder> call() throws Exception {
        List<SpikeOrder> list = new LinkedList<>();
        JSONArray jsonArray = commonUti.httpRequest(url, "GET");
        list = jsonArray.toJavaList(SpikeOrder.class);
        return list;
    }
}
