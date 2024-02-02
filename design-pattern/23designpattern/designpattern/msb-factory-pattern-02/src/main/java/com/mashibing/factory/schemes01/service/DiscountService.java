package com.mashibing.factory.schemes01.service;

import com.mashibing.factory.schemes01.entity.DiscountResult;

/**
 * 打折券服务
 * @author spikeCong
 * @date 2022/9/8
 **/
public class DiscountService {

    public DiscountResult sendDiscount(String uid, String awardNumber){

        System.out.println("向用户发送一张打折券: " + uid +  " , " + awardNumber);
        return new DiscountResult("200","发放打折券成功!");
    }

}
