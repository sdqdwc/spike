package com.mw.chain;

import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-15 23:35
 */
public class Chain {
    private List<ChainHandler> handlers;

    private int index = 0;

    public Chain(List<ChainHandler> handlers) {
        this.handlers = handlers;
        //System.out.println(handlers.size());
    }

    public void proceed(){
        if(index >= handlers.size()){
            return;
        }
        handlers.get(index++).execute(this);
    }
}
