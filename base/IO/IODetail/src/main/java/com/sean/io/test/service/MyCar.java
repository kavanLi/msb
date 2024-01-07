package com.sean.io.test.service;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 19:04
 */
public class MyCar implements Car {

    @Override
    public String ooxx(String msg) {
//        System.out.println("server,get client arg:"+msg);
        return "server res "+ msg;
    }
}
