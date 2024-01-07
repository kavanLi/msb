package com.mashibing.factory.factory_method;

import com.mashibing.factory.simple.Car;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class JinBei implements Car {

    @Override
    public void carName() {
        System.out.println("金杯");
    }
}
