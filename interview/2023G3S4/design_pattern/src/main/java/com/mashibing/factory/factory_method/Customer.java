package com.mashibing.factory.factory_method;

import com.mashibing.factory.simple.Car;
import com.mashibing.factory.simple.CarFactory;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class Customer {

    public static void main(String[] args) {

        Car car = new JinBeiFactory().getCar();
        car.carName();
    }

}
