package com.mashibing.factory.factory_method.factory.impl;

import com.mashibing.factory.factory_method.factory.FreeGoodsFactory;
import com.mashibing.factory.simple_factory.service.IFreeGoods;
import com.mashibing.factory.simple_factory.service.impl.SmallGiftFreeGoods;

/**
 * 生产小礼品发放接口-具体工厂
 * @author spikeCong
 * @date 2022/9/9
 **/
public class SmallGiftFreeGoodsFactory implements FreeGoodsFactory {

    @Override
    public IFreeGoods getInstance() {
        return new SmallGiftFreeGoods();
    }
}
