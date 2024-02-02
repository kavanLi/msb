package com.mashibing.example01.builder;

import com.mashibing.example01.product.Bike;

/**
 * 哈罗单车建造者
 * @author spikeCong
 * @date 2022/9/19
 **/
public class HelloBikeBuilder  extends Builder{

    @Override
    public void buildFrame() {
        System.out.println("制作碳纤维车架");
        mBike.setFrame("碳纤维车架");
    }

    @Override
    public void buildSeat() {
        System.out.println("制作橡胶车座");
        mBike.setFrame("橡胶车座");
    }

    @Override
    public Bike createBike() {
        return mBike;
    }
}
