package com.mashibing.factory.simple;

import com.mashibing.factory.simple.Car;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class Wuling implements Car {


    @Override
    public void carName() {
        System.out.println("五菱");
    }
}
