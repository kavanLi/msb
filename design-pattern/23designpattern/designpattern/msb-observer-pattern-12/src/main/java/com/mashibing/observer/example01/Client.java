package com.mashibing.observer.example01;

import java.util.Observer;

/**
 * @author spikeCong
 * @date 2022/10/11
 **/
public class Client {

    public static void main(String[] args) {



        //创建目标类
        Subject subject = new ConcreteSubject();

        //注册观察者,注册多个
        subject.attach(new ConcreteObserver1());
        subject.attach(new ConcreteObserver2());

        //具体的主题内部发生改变,给所有注册的观察者发送通知
        subject.notifyObservers();
    }
}
