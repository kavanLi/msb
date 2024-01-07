package com.mashibing.singleton;

/**
 * @author spikeCong
 * @date 2023/3/8
 **/
public class Singleton03 {

    private Singleton03() {
        System.out.println(Thread.currentThread().getName());
    }

    private volatile static Singleton03 instance;

    //3.提供全局访问点，供外部获取单例对象
    public static  Singleton03 getInstance(){

        //第一次判断,如果instance不为null,不进入抢锁阶段,直接返回实例
        if(instance == null){
            //第二次判断 抢到锁之后再次进行判断是否为null，避免多线程下重复创建
            synchronized(Singleton03.class){
                if(instance == null){
                    instance = new Singleton03();
                }
            }
        }

        return instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                instance = getInstance();
            }).start();
        }
    }


}
