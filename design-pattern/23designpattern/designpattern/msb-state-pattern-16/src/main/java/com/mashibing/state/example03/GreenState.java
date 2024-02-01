package com.mashibing.state.example03;

/**
 * @author spikeCong
 * @date 2022/10/17
 **/
public class GreenState implements State {

    @Override
    public void switchToGreen(TrafficLight trafficLight) {
        System.out.println("当前是绿灯,无需切换!");
    }

    @Override
    public void switchToYellow(TrafficLight trafficLight) {
        System.out.println("黄灯亮起...时长: 10秒");
    }

    @Override
    public void switchToRed(TrafficLight trafficLight) {
        System.out.println("绿灯不能够切换为红灯!");
    }
}
