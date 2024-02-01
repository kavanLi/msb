package com.mashibing.flyweight.example01;

/**
 * @author spikeCong
 * @date 2022/10/10
 **/
public class Client {

    public static void main(String[] args) {

        //获取工厂对象
        FlyweightFactory factory = new FlyweightFactory();

        //通过工厂对象获取共享的享元对象
        Flyweight a1 = factory.getFlyweight("A");
        a1.operation("a1ExState");

        Flyweight a2 = factory.getFlyweight("A");
        a2.operation("a2ExState");
        System.out.println(a1 == a2);

        //获取非共享的享元对象
        UnsharedFlyweight u1 = new UnsharedFlyweight("A");
        UnsharedFlyweight u2 = new UnsharedFlyweight("A");
        System.out.println(u1 == u2);
    }
}
