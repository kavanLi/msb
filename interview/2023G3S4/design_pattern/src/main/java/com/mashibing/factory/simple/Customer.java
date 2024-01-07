package com.mashibing.factory.simple;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class Customer {

    public static void main(String[] args) {
        Car car = CarFactory.getCar("1");
        car.carName();
    }

}
