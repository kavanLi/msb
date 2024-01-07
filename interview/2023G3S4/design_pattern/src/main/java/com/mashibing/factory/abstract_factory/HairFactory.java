package com.mashibing.factory.abstract_factory;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class HairFactory implements ApplicationsFactory {
    @Override
    public AbstractTV createTV() {
        return new HairTV();
    }

    @Override
    public AbstractFreezer createFreezer() {
        return new HairFreezer();
    }
}
