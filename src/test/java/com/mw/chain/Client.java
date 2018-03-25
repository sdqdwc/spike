package com.mw.chain;

/**
 * @author WangCH
 * @create 2018-03-15 23:32
 */
public class Client {
    static class HandlerA extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("a");
        }
    }
    static class HandlerB extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("b");
        }
    }
    static class HandlerC extends Handler{
        @Override
        protected void handleProcess() {
            System.out.println("c");
        }
    }

    public static void main(String[] args) {
        Handler handlerA = new HandlerA();
        Handler handlerB = new HandlerB();
        Handler handlerC = new HandlerC();
        handlerA.setSuccessor(handlerB);
        handlerB.setSuccessor(handlerC);
        handlerA.execute();


    }


}
