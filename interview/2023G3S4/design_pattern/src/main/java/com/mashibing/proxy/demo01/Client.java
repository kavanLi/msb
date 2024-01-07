package com.mashibing.proxy.demo01;

import org.junit.Test;

/**
 * @author spikeCong
 * @date 2023/3/19
 **/
public class Client {

    @Test
    public void testStaticProxy(){

        //目标对象
        UserDaoImpl userDao = new UserDaoImpl();
        //代理对象
        UserDaoProxy proxy = new UserDaoProxy(userDao);
        proxy.save();
    }
}
