package com.mashibing.chain.example01;

/**
 * 抽象的处理者类
 * @author spikeCong
 * @date 2022/10/14
 **/
public abstract class Handler {

    //后继处理者的引用
    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    public abstract void handle(RequestData requestData);
}
