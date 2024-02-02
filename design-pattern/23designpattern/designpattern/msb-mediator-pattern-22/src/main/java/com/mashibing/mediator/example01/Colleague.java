package com.mashibing.mediator.example01;

/**
 * 抽象同事类
 * @author spikeCong
 * @date 2022/10/21
 **/
public abstract class Colleague {

    private Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    public Mediator getMediator() {
        return mediator;
    }

    //同事间进行交互的抽象方法
    public abstract void exec(String key);
}
