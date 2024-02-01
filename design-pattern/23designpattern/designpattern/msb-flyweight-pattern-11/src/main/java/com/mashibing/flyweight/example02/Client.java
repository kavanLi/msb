package com.mashibing.flyweight.example02;

/**
 * @author spikeCong
 * @date 2022/10/10
 **/
public class Client {

    public static void main(String[] args) {

        GobangFactory instance = GobangFactory.getInstance();

        //获取3颗黑子
        GobangFlyweight b1 = instance.getGobang("b");
        GobangFlyweight b2 = instance.getGobang("b");
        GobangFlyweight b3 = instance.getGobang("b");
        System.out.println("判断黑子是否是同一对象: " + (b1 == b2));

        GobangFlyweight w1 = instance.getGobang("w");
        GobangFlyweight w2 = instance.getGobang("w");
        System.out.println("判断白子是否是同一对象: " + (w1 == w2));

        //显示棋子
        b1.display();
        b2.display();
        b3.display();
        w1.display();
        w2.display();
    }
}
