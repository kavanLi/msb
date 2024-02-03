package com.msb.lambda;

import java.util.function.Consumer;

public class ConsumerExample {
    //客户需要购物
    public static void shopping(double x, Consumer consumer) {
        consumer.accept(x);
    }

    public static void main(String[] args) {
        Consumer<Double> c1 = (x) -> {
            System.out.println("小李花了" + x + "元，买了一辆车");
        };
        shopping(20000, c1);

        Consumer<Double> c2 = (x) -> {
            System.out.println("小明花了" + x + "元，买了一堆化妆品");
        };
        shopping(50000, c2);
    }
}
