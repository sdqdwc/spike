package com.mw.chain;

/**
 * @author WangCH
 * @create 2018-03-15 23:35
 */
public abstract class ChainHandler {

    public void execute(Chain chain){
        handleProcess();
        chain.proceed();
    }

    protected abstract void handleProcess();

}
