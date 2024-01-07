package com.mashibing.factory.abstract_factory;


/**
 * @author spikeCong
 * @date 2023/3/9
 **/
public interface ApplicationsFactory {

    AbstractTV createTV();

    AbstractFreezer createFreezer();
}
