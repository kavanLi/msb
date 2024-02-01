package com.mashibing.observer.example01;

/**
 * 具体的观察者
 * @author spikeCong
 * @date 2022/10/11
 **/
public class ConcreteObserver1 implements Observer {

    @Override
    public void update() {
        System.out.println("ConcreteObserver1 得到通知,更新状态! !");
    }
}
