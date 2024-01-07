package com.sean.io.test.service;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 19:08
 */
public class MyFly implements Fly {

    @Override
    public void xxoo(String msg) {
        System.out.println("server,get client arg:"+msg);
    }
}
