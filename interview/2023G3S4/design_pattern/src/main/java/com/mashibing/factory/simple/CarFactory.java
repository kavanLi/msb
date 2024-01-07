package com.mashibing.factory.simple;

/**
 * 简单工厂模式
 * @author spikeCong
 * @date 2023/3/9
 **/
public class CarFactory {

    //type值： 0 奔驰，1 五菱
    public static Car getCar(String type){
        if(type.equals("0")){
            return new Bench();
        }else if(type.equals("1")){
            return new Wuling();
        }else{
            return null;
        }
    }

}
