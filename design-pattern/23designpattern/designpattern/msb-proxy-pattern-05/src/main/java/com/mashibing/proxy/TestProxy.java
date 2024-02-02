package com.mashibing.proxy;

import com.mashibing.proxy.example01.IUserDao;
import com.mashibing.proxy.example01.UserDaoImpl;
import com.mashibing.proxy.example01.UserDaoProxy;
import com.mashibing.proxy.example02.ProxyFactory;
import com.mashibing.proxy.example03.User;
import com.mashibing.proxy.example03.UserLogProxy;
import com.mashibing.proxy.example03.UserServiceImpl;
import org.junit.Test;

import java.util.List;

/**
 * @author spikeCong
 * @date 2022/9/22
 **/
public class TestProxy {


    /**
     * 静态代理
     *     优点: 可以在不修改目标类的前提下,扩展目标类的功能
     *     缺点:
     *        1.冗余.由于代理对象要实现和目标对象一致的接口,会产生很多的代理.
     *        2.不易维护.一旦接口中增加方法,目标对象和代理对象都要进行修改.
     */
    @Test
    public void testStaticProxy(){

        //目标类
        IUserDao dao = new UserDaoImpl();

        //代理对象
        UserDaoProxy proxy = new UserDaoProxy(dao);
        proxy.save();
    }

    public static void main(String[] args) {

        IUserDao userDao = new UserDaoImpl();
        System.out.println(userDao.getClass()); //目标对象的信息

        IUserDao proxy = (IUserDao) new ProxyFactory(userDao).getProxyInstance();//获取代理对象
        System.out.println(proxy.getClass());
        proxy.save();//代理方法

        while (true){}
    }

    @Test
    public void testCglibProxy(){

        //目标对象
        UserServiceImpl userService = new UserServiceImpl();
        System.out.println(userService.getClass());

        //代理对象
        UserServiceImpl proxy = (UserServiceImpl) new UserLogProxy().getLogProxy(userService);
        System.out.println(proxy.getClass());

        List<User> list = proxy.findUserList();
        System.out.println("用户信息: " +list);
    }

}
