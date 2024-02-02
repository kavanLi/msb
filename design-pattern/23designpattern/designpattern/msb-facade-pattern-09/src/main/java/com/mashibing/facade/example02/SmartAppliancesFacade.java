package com.mashibing.facade.example02;

/**
 * 智能音箱外观类
 * @author spikeCong
 * @date 2022/10/5
 **/
public class SmartAppliancesFacade {

    private Light light;

    private TV tv;

    private AirCondition airCondition;

    public SmartAppliancesFacade() {
        this.light =new Light();
        this.tv = new TV();
        this.airCondition = new AirCondition();
    }

    public void say(String message){
        if(message.contains("打开")){
            on();
        }else if(message.contains("关闭")){
            off();
        }else{
            System.out.println("对不起没有听清楚您说什么! 请重新再说一遍");
        }

    }


    //起床后 语音开启 电灯 电视 空调
    private void on() {
        System.out.println("起床了!");
        light.on();
        tv.on();
        airCondition.on();
    }

    //睡觉前 语音关闭 电灯 电视 空调
    private void off() {
        System.out.println("睡觉了!");
        light.off();
        tv.off();
        airCondition.off();
    }
}
