package com.mashibing.factory;

import com.mashibing.factory.simple_factory.controller.DeliverController;
import com.mashibing.factory.simple_factory.entity.AwardInfo;
import com.mashibing.factory.simple_factory.entity.ResponseResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author spikeCong
 * @date 2022/9/9
 **/
public class TestApi02 {

    DeliverController deliverController = new DeliverController();

    @Test
    public void test01(){

        //1. 发放打折券优惠
        AwardInfo info1 = new AwardInfo();
        info1.setUid("1001");
        info1.setAwardTypes(1);
        info1.setAwardNumber("DEL12345");

        ResponseResult result = deliverController.awardToUser(info1);
        System.out.println(result);

    }

    @Test
    public void test02(){

        //2. 发放优酷会员
        AwardInfo info2 = new AwardInfo();
        info2.setUid("1002");
        info2.setAwardTypes(2);
        info2.setAwardNumber("DW12345");
        Map<String,String> map = new HashMap<>();
        map.put("phone","13512341234");
        info2.setExtMap(map);

        ResponseResult result1 = deliverController.awardToUser(info2);
        System.out.println(result1);

    }

    @Test
    public void test03(){

        //3. 发放小礼品
        AwardInfo info3 = new AwardInfo();
        info3.setUid("1003");
        info3.setAwardTypes(3);
        info3.setAwardNumber("SM12345");
        Map<String,String> map2 = new HashMap<>();
        map2.put("username","大远");
        map2.put("phone","13512341234");
        map2.put("address","北京天安门");
        info3.setExtMap(map2);

        ResponseResult result2 = deliverController.awardToUser(info3);
        System.out.println(result2);
    }
}
