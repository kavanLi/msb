package com.mashibing.observer.example03;

import com.mashibing.observer.example02.LotteryResult;

/**
 * 开奖服务接口
 * @author spikeCong
 * @date 2022/10/12
 **/
public abstract class LotteryService {

    private EventManager eventManager;

    public LotteryService() {
        //设置事件的类型
        eventManager = new EventManager(EventManager.EventType.MQ,EventManager.EventType.Message);
        //订阅
        eventManager.subscribe(EventManager.EventType.Message,new MessageEventListener());
        eventManager.subscribe(EventManager.EventType.MQ,new MQEventListener());
    }

    public LotteryResult lotteryAndMsg(String uId){
        LotteryResult lottery = lottery(uId);
        //发送通知
        eventManager.notify(EventManager.EventType.Message,lottery);
        eventManager.notify(EventManager.EventType.MQ,lottery);

        return lottery;
    }

    public abstract LotteryResult lottery(String uId);
}
