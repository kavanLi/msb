package com.mashibing.singleton.demo04;

import com.mashibing.singleton.demo03.Singleton_03;

/**
 * 单例模式-双重校验
 * @author spikeCong
 * @date 2022/9/5
 **/
public class Singleton_04 {

    //1. 私有构造方法
    private Singleton_04(){

    }

    //2. 在本类中创建私有静态的全局对象
    // 使用 volatile保证变量可见性,屏蔽指令重排序
    private volatile static Singleton_04 instance;

    //3. 获取单例对象的静态方法
    public static  Singleton_04 getInstance(){

        //第一次判断,如果instance不为null,不进入抢锁阶段,直接返回实例
        if(instance == null){
            synchronized (Singleton_04.class){
                //第二次判断,抢到锁之后再次进行判断,判断是否为null
                if(instance == null){

                    instance = new Singleton_04();
                    /**
                     * 上面的创建对象的代码,在JVM中被分为三步:
                     *      1.分配内存空间
                     *      2.初始化对象
                     *      3.将instance指向分配好的内存空间
                     */

                }
            }
        }

        return instance;
    }

}
