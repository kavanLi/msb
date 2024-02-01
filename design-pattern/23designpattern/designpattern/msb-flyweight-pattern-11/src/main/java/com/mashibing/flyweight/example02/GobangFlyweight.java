package com.mashibing.flyweight.example02;

/**
 * 抽象享元类: 五子棋
 * @author spikeCong
 * @date 2022/10/10
 **/
public abstract class GobangFlyweight {

    public abstract String getColor();

    public void display(){
        System.out.println("棋子颜色: " + this.getColor());
    }

}
