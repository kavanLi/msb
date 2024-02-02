package com.mashibing.factory.simple_factory.service.impl;

import com.mashibing.factory.simple_factory.entity.AwardInfo;
import com.mashibing.factory.simple_factory.entity.ResponseResult;
import com.mashibing.factory.simple_factory.service.IFreeGoods;

/**
 * 模拟打折券服务
 * @author spikeCong
 * @date 2022/9/8
 **/
public class DiscountFreeGoods implements IFreeGoods {

    @Override
    public ResponseResult sendFreeGoods(AwardInfo awardInfo) {

        System.out.println("向用户发放一张打折券: " + awardInfo.getUid() + " , " + awardInfo.getAwardNumber());
        return new ResponseResult("200","打折券发放成功!");
    }
}
