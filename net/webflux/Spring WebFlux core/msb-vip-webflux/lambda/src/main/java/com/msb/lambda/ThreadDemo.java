package com.msb.lambda;

public class ThreadDemo {
    public static void main(String[] args) {
        Object target1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("命令式编程");
            }
        };
        new Thread((Runnable) target1).start();

        //jdk8
        Object target2 = (Runnable)()-> System.out.println("函数式编程");
        Runnable target3 = ()-> System.out.println("函数式编程");
        System.out.println(target2 == target3);
        new Thread((Runnable) target2).start();
    }
}

