package com.mashibing.factory.simple_factory.service.impl;

import com.mashibing.factory.simple_factory.entity.AwardInfo;
import com.mashibing.factory.simple_factory.entity.ResponseResult;
import com.mashibing.factory.simple_factory.service.IFreeGoods;

/**
 * 优酷 会员服务
 * @author spikeCong
 * @date 2022/9/8
 **/
public class YouKuMemberFreeGoods implements IFreeGoods {

    @Override
    public ResponseResult sendFreeGoods(AwardInfo awardInfo) {

        String phone = awardInfo.getExtMap().get("phone");
        System.out.println("发放优酷会员成功,绑定手机号: " + phone);
        return new ResponseResult("200","优酷会员发放成功!");
    }
}
