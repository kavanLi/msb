package com.mashibing.factory.simple_factory.service.impl;

import com.alibaba.fastjson.JSON;
import com.mashibing.factory.simple_factory.entity.AwardInfo;
import com.mashibing.factory.simple_factory.entity.ResponseResult;
import com.mashibing.factory.simple_factory.entity.SmallGiftInfo;
import com.mashibing.factory.simple_factory.service.IFreeGoods;

import java.util.UUID;

/**
 * 小礼品发放服务
 * @author spikeCong
 * @date 2022/9/8
 **/
public class SmallGiftFreeGoods implements IFreeGoods {

    @Override
    public ResponseResult sendFreeGoods(AwardInfo awardInfo) {

        SmallGiftInfo smallGiftInfo = new SmallGiftInfo();
        smallGiftInfo.setUserPhone(awardInfo.getExtMap().get("phone"));
        smallGiftInfo.setUserName(awardInfo.getExtMap().get("username"));
        smallGiftInfo.setAddress(awardInfo.getExtMap().get("address"));
        smallGiftInfo.setOrderId(UUID.randomUUID().toString());

        System.out.println("小礼品发放成,请注意查收: " + JSON.toJSON(smallGiftInfo));
        return new ResponseResult("200","小礼品发送成功",smallGiftInfo);
    }
}
