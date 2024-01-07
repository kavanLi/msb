package com.mashibing02.abstract_factory;

/**
 * 抽象工厂模式
 *      优点: 生产不同的产品系列的时候,可以使用抽象工厂模式
 *      缺点: 增加系统的复杂度
 * @author spikeCong
 * @date 2023/3/20
 **/
public class HisenseFactory implements AppliancesFactory {
    @Override
    public AbstractTV createTV() {
        return new HisenseTV();
    }

    @Override
    public AbstractFreezer createFreezer() {
        return new HisenseFreezer();
    }
}
