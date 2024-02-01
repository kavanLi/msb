package com.mashibing.state.example01;

/**
 * 上下文类
 * @author spikeCong
 * @date 2022/10/17
 **/
public class Context {

    //维持一个对状态对象的有引用
    private State currentState;

    public Context() {
        this.currentState = null;
    }

    public Context(State currentState) {
        this.currentState = currentState;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "Context{" +
                "currentState=" + currentState +
                '}';
    }
}
