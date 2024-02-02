package com.mashibing.mediator.example01;

/**
 * @author spikeCong
 * @date 2022/10/21
 **/
public class Client {

    public static void main(String[] args) {

        //创建中介者
        Mediator mediator = new MediatorImpl();

        //创建同事对象
        Colleague c1 = new ConcreteColleagueA(mediator);
        c1.exec("key-A");

        Colleague c2 = new ConcreteColleagueB(mediator);
        c2.exec("key-B");
    }
}
