package com.mashibing02.abstract_factory;

/**
 * @author spikeCong
 * @date 2023/3/20
 **/
public class HariFactory implements AppliancesFactory {
    @Override
    public AbstractTV createTV() {
        return new HairTV();
    }

    @Override
    public AbstractFreezer createFreezer() {
        return new HairFreezer();
    }
}
