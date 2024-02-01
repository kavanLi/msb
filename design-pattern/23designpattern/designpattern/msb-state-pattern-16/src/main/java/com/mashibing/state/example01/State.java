package com.mashibing.state.example01;

/**
 * 抽象状态接口
 * @author spikeCong
 * @date 2022/10/17
 **/
public interface State {

    //声明抽象方法,不同具体状态类可以有不同的实现
    void handle(Context context);
}
