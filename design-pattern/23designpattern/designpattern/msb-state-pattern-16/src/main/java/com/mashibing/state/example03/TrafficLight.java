package com.mashibing.state.example03;

/**
 * 交通灯类
 * @author spikeCong
 * @date 2022/10/17
 **/
public class TrafficLight {

    //初始化-红灯
    State state =  new RedState();

    public void setState(State state) {
        this.state = state;
    }

    //切换为绿灯,通行状态
    public void switchToGreen(){
        state.switchToGreen(this);
    }

    //切换为黄灯,警示状态
    public void switchToYellow() {
        state.switchToYellow(this);
    }

    //切换为红灯,禁止状态
    public void switchToRed(){
        state.switchToRed(this);
    }

}
