package com.mashibing.example01.builder;

import com.mashibing.example01.product.Bike;

/**
 * 摩拜单车建造者
 * @author spikeCong
 * @date 2022/9/19
 **/
public class MobikeBuilder extends Builder {

    @Override
    public void buildFrame() {
        System.out.println("制作车架!");
        mBike.setFrame("铝合金车架");
    }

    @Override
    public void buildSeat() {
        System.out.println("制作车座");
        mBike.setSeat("真皮车座");
    }

    @Override
    public Bike createBike() {
        return mBike;
    }
}
