package com.mashibing.factory.factory_method.factory;

import com.mashibing.factory.factory_method.factory.impl.DiscountFreeGoodsFactory;
import com.mashibing.factory.factory_method.factory.impl.SmallGiftFreeGoodsFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂的工厂,用来创建工厂类对象.
 * @author spikeCong
 * @date 2022/9/9
 **/
public class FreeGoodsFactoryMap {

    //创建map集合,保存工厂对象
    private static final Map<Integer,FreeGoodsFactory> cachedFactories = new HashMap<>();

    static{
        cachedFactories.put(1,new DiscountFreeGoodsFactory());
        cachedFactories.put(2,new SmallGiftFreeGoodsFactory());
    }

    public static FreeGoodsFactory getParserFactory(Integer type){
        if(type == 1){

            FreeGoodsFactory freeGoodsFactory = cachedFactories.get(1);
            return freeGoodsFactory;
        }else if(type == 2){

            FreeGoodsFactory freeGoodsFactory = cachedFactories.get(2);
            return freeGoodsFactory;
        }

        return null;
    }
}
