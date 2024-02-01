package com.mashibing.singleton.test02;

import com.mashibing.singleton.demo05.Singleton_05;
import com.mashibing.singleton.demo06.Singleton_06;

import java.lang.reflect.Constructor;

/**
 * 反射对于单例的破坏
 * @author spikeCong
 * @date 2022/9/6
 **/
public class Test_Reflect {

    public static void main(String[] args) throws Exception {

        Class<Singleton_05> clazz = Singleton_05.class;

        Constructor<Singleton_05> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true); //设置为true后,就可以对类中的私有成员进行操作

//        Singleton_06 instance = constructor.newInstance();

        Singleton_05 instance = constructor.newInstance();
        System.out.println(instance);
    }
}
