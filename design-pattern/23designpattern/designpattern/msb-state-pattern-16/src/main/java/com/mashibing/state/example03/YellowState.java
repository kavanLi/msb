package com.mashibing.state.example03;

/**
 * @author spikeCong
 * @date 2022/10/17
 **/
public class YellowState implements State {

    @Override
    public void switchToGreen(TrafficLight trafficLight) {
        System.out.println("绿灯亮起...时长:60秒!");
    }

    @Override
    public void switchToYellow(TrafficLight trafficLight) {
        System.out.println("当前是黄灯,无需切换!");
    }

    @Override
    public void switchToRed(TrafficLight trafficLight) {
        System.out.println("红灯亮起...时长:90秒!");
    }
}
