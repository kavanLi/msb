package com.mashibing02.simple;

/**
 * 简单工厂模式优点:
 *         1.隐藏了对象的创建细节
 *         2.便于维护和升级
 *         3.方便扩展系统的功能
 * 简单工厂模式缺点:
 *          1.违背了开闭原则
 * @author spikeCong
 * @date 2023/3/20
 **/
public class CarFactory {

    //type: 0 奔驰 , 1 五菱
    public static Car getCar(String type){
        if(type.equals("0")){
            return new Bench();
        }else if(type.equals("1")){
            return new Wuling();
        }

        return null;
    }
}
