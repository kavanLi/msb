package com.mashibing.factory.abstract_factory;

/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public class HisenseFactory implements ApplicationsFactory {
    @Override
    public AbstractTV createTV() {
        return new HisenseTV();
    }

    @Override
    public AbstractFreezer createFreezer() {
        return new HisenseFreezer();
    }
}
