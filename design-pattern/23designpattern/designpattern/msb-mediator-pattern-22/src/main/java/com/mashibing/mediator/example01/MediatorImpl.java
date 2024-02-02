package com.mashibing.mediator.example01;

/**
 * 具体中介者
 * @author spikeCong
 * @date 2022/10/21
 **/
public class MediatorImpl implements Mediator {

    @Override
    public void apply(String key) {
        System.out.println("最终中介者执行的操作,key为" + key);
    }
}
