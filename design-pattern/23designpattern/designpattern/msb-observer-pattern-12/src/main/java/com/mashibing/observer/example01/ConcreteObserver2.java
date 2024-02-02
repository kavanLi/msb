package com.mashibing.observer.example01;

/**
 * @author spikeCong
 * @date 2022/10/11
 **/
public class ConcreteObserver2  implements Observer{


    @Override
    public void update() {
        System.out.println("ConcreteObserver2 得到通知,更新状态! !");
    }
}
