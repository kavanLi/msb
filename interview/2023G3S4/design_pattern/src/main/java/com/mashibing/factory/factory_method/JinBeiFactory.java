package com.mashibing.factory.factory_method;

import com.mashibing.factory.simple.Car;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class JinBeiFactory implements CarFactory {

    @Override
    public Car getCar() {
        return new JinBei();
    }
}
