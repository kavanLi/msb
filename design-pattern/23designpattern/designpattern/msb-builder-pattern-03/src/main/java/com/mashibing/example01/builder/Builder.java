package com.mashibing.example01.builder;

import com.mashibing.example01.product.Bike;

/**
 * 抽象建造者类
 * @author spikeCong
 * @date 2022/9/19
 **/
public abstract class Builder {

    protected Bike mBike = new Bike();

    public abstract void buildFrame();

    public abstract void buildSeat();

    public abstract Bike createBike();
}
