package com.mw.chain;

/**
 * @author WangCH
 * @create 2018-03-15 23:30
 */
public abstract class Handler {

    protected Handler successor;

    public Handler getSuccessor() {
        return successor;
    }

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public void execute(){
        handleProcess();
        if(successor != null){
            successor.execute();
        }
    }

    protected abstract void handleProcess();

}
