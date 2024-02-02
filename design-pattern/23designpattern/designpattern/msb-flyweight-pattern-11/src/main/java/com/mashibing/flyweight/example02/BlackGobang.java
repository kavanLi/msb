package com.mashibing.flyweight.example02;

/**
 * 共享享元类-黑色棋子
 * @author spikeCong
 * @date 2022/10/10
 **/
public class BlackGobang extends GobangFlyweight {


    @Override
    public String getColor() {
        return "黑色";
    }
}
