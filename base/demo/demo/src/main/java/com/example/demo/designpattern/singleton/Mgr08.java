package com.example.demo.designpattern.singleton;

import org.apache.poi.ss.formula.functions.T;

/**
 * 枚举单例不仅可以解决线程同步，还可以防止反序列化。
 * 枚举类本身是线程安全的，因为枚举实例是在类加载时创建的，并且JVM保证了类加载过程的线程安全。
 *
 * 在构造函数中初始化 dateTimeFormatter 的方式不会引起线程安全问题，因为在 Java 中，对象的构造过程是线程安全的。这意味着当一个线程正在执行构造函数时，其他线程无法同时访问该对象，因此不会出现多个线程同时初始化 dateTimeFormatter 的情况。
 *
 * 因此，您的代码中的构造函数是线程安全的。每个 CustomDateTimeFormatter 实例在构造时都会创建一个独立的 DateTimeFormatter 实例，因此每个实例都是线程安全的。
 */
public enum Mgr08 {

    INSTANCE;

    private T data;

    public void m(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public static void main(String[] args) {
        for(int i=0; i<100; i++) {
            new Thread(()->{
                System.out.println(Mgr08.INSTANCE.hashCode());
            }).start();
        }
    }

}
