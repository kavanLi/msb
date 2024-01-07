package com.mashibing02.factory_method;

import com.mashibing02.simple.Car;

/**
 * @author spikeCong
 * @date 2023/3/20
 **/
public class Customer {

    public static void main(String[] args) {

        Car car = new WulingFactory().getCat();
        car.carName();
    }
}
