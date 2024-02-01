package com.mashibing.proxy.example04;

/**
 * @author spikeCong
 * @date 2022/11/20
 **/
public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("执行了被代理角色。。。");
    }
}
