package com.mashibing02.factory_method;

import com.mashibing02.simple.Car;
import com.mashibing02.simple.Wuling;

/**
 * @author spikeCong
 * @date 2023/3/20
 **/
public class WulingFactory implements CarFactory {


    @Override
    public Car getCat() {
        return new Wuling();
    }
}
