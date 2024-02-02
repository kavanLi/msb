package com.mashibing.observer.example01;

/**
 * 抽象目标类
 * @author spikeCong
 * @date 2022/10/11
 **/
public interface Subject {

    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
