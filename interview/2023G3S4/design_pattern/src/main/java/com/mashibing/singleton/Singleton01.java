package com.mashibing.singleton;

/**
 * 饿汉式
 * @author spikeCong
 * @date 2023/3/8
 **/
public class Singleton01 {

    //1.私有化构造方法
    private Singleton01(){

    }

    //2.创建一个私有静态的全局唯一对象
    private static Singleton01 instance = new Singleton01();

    //3.提供全局访问点，供外部获取单例对象
    public static Singleton01 getInstance(){

        return instance;
    }
}
