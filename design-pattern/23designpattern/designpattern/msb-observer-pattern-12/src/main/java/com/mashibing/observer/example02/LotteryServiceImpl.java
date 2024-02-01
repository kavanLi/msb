package com.mashibing.observer.example02;

import java.util.Date;

/**
 * @author spikeCong
 * @date 2022/10/12
 **/
public class LotteryServiceImpl implements LotteryService {

    //注入摇号服务
    private DrawHouseService houseService = new DrawHouseService();

    @Override
    public LotteryResult lottery(String uId) {
        //1.摇号
        String result = houseService.lots(uId);

        //2.发短信
        System.out.println("发送短信通知用户,ID为: " + uId +",您的摇号结果如下: " + result);

        //3.发送MQ信息
        System.out.println("记录用户摇号结果到MQ,ID为: " + uId +",摇号结果: " + result);

        return new LotteryResult(uId,result,new Date());
    }
}
