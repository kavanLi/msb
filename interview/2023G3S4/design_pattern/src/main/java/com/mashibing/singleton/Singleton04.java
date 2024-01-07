package com.mashibing.singleton;

/**
 * 静态内部类
 * @author spikeCong
 * @date 2023/3/8
 **/
public class Singleton04 {

    //静态内部类的特点，外部类的加载不影响内部类，同时解决了 按需加载、线程安全的问题，并实现了代码简洁
    private static class SingletonHandler{

        private static Singleton04 instance = new Singleton04();
    }

    private Singleton04() {
        if(SingletonHandler.instance != null){
            throw new RuntimeException("不允许非法访问！ ！ ！");
        }
    }

    public  static Singleton04 getInstance(){
        return SingletonHandler.instance;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton04 instance = getInstance();
            }).start();
        }
    }
}
