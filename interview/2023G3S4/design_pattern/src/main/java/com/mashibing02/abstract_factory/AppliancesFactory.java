package com.mashibing02.abstract_factory;

/**
 * @author spikeCong
 * @date 2023/3/20
 **/
public interface AppliancesFactory {

    AbstractTV createTV();

    AbstractFreezer createFreezer();
}
