package com.mashibing.singleton;

import java.lang.reflect.Constructor;

/**
 * @author spikeCong
 * @date 2023/3/8
 **/
public class ReflectTest {

    public static void main(String[] args) throws Exception {

        Class<Singleton05> clazz = Singleton05.class;

        Constructor<Singleton05> con = clazz.getDeclaredConstructor(String.class,int.class);

        con.setAccessible(true);

        Singleton05 obj1 = con.newInstance();
        Singleton05 obj2 = con.newInstance();

        System.out.println(obj1 == obj2);
    }

}
