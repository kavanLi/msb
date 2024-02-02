package com.mashibing.mediator.example02;

/**
 * 抽象中介者
 * @author spikeCong
 * @date 2022/10/21
 **/
public abstract class Mediator {

    //创建联络方法
    public abstract void contact(String message, Person person);
}
