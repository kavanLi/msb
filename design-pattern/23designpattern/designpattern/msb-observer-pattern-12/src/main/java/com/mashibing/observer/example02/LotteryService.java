package com.mashibing.observer.example02;

/**
 * 开奖服务接口
 * @author spikeCong
 * @date 2022/10/12
 **/
public interface LotteryService {

    //开奖之后的业务操作
    public LotteryResult lottery(String uId);

}
