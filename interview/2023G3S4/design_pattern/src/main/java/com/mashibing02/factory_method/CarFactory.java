package com.mashibing02.factory_method;

import com.mashibing02.simple.Car;

/**
 * 工厂方法模式优缺点
 *      优点: 根据工厂名字就可以获取对应的产品,不需要对原工厂进行修改,满足开闭原则
 *      缺点: 增加系统的复杂度.
 * @author spikeCong
 * @date 2023/3/20
 **/
public interface CarFactory {

    Car getCat();

}
