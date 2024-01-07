package com.mashibing.singleton;

/**
 * 懒汉式
 * @author spikeCong
 * @date 2023/3/8
 **/
public class Singleton02 {

    private Singleton02() {

    }

    private static Singleton02 instance;

    //3.提供全局访问点，供外部获取单例对象
    public static synchronized Singleton02 getInstance(){

        if(instance == null){
            instance = new Singleton02();
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

    /*
     * 打印结果， 构造方法被多次调用
     * Thread-0
        Thread-3
        Thread-2
        Thread-1
     *
     */
}
