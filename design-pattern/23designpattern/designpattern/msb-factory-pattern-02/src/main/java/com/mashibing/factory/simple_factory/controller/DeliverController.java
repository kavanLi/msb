package com.mashibing.factory.simple_factory.controller;

import com.mashibing.factory.simple_factory.entity.AwardInfo;
import com.mashibing.factory.simple_factory.entity.ResponseResult;
import com.mashibing.factory.simple_factory.factory.FreeGoodsFactory;
import com.mashibing.factory.simple_factory.service.IFreeGoods;

/**
 * 发放奖品接口
 * @author spikeCong
 * @date 2022/9/9
 **/
public class DeliverController {

    //发放奖品
    public ResponseResult awardToUser(AwardInfo awardInfo){

        try {
            IFreeGoods freeGoods = FreeGoodsFactory.getInstance(awardInfo.getAwardTypes());
            ResponseResult responseResult = freeGoods.sendFreeGoods(awardInfo);
            return responseResult;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult("201","奖品发放失败!");
        }
    }

}
