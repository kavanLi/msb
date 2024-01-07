package com.mashibing02.factory_method;

import com.mashibing02.simple.Car;
import com.mashibing02.simple.JinBen;

/**
 * @author spikeCong
 * @date 2023/3/20
 **/
public class JinbeiFactory implements CarFactory {

    @Override
    public Car getCat() {
        return new JinBen();
    }
}
