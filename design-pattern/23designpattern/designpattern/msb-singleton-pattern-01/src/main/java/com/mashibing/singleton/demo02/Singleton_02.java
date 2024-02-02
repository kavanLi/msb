package com.mashibing.singleton.demo02;

import com.mashibing.singleton.demo01.Singleton_01;

/**
 * 单例模式-懒汉式
 *    特点: 支持延时加载,只有调用getInstance方法时,才会创建对象.
 * @author spikeCong
 * @date 2022/9/5
 **/
public class Singleton_02 {

    //1. 私有构造方法
    private Singleton_02(){

    }

    //2. 在本类中创建私有静态的全局对象
    private static Singleton_02 instance;


    //3. 通过判断对象是否被初始化,来选择是否创建对象
    public static  Singleton_02 getInstance(){

        if(instance == null){

            instance = new Singleton_02();
        }
        return instance;
    }

}
