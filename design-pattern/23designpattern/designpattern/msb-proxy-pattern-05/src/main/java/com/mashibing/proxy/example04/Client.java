package com.mashibing.proxy.example04;

/**
 * @author spikeCong
 * @date 2022/11/20
 **/
public class Client {

    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        System.out.println(realSubject); // 目标对象信息
        Subject proxy = (Subject) new ProxyFactory(realSubject).getProxyInstance();
        System.out.println(proxy);
        proxy.request();
    }

}
