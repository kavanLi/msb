package com.mashibing.singleton.test01;

import com.mashibing.singleton.demo01.Singleton_01;
import com.mashibing.singleton.demo02.Singleton_02;
import com.mashibing.singleton.demo03.Singleton_03;
import com.mashibing.singleton.demo04.Singleton_04;
import com.mashibing.singleton.demo05.Singleton_05;
import org.junit.Test;

/**
 * @author spikeCong
 * @date 2022/9/5
 **/
public class Test_Singleton {

    //测试饿汉式
    @Test
    public void test01(){

        Singleton_01 instance = Singleton_01.getInstance();

        Singleton_01 instance1 = Singleton_01.getInstance();

        System.out.println(instance == instance1);
    }

    //测试-懒汉式
    @Test
    public void test02(){

        for (int i = 0; i < 500; i++) {
            new Thread(() -> {
//                Singleton_02 instance = Singleton_02.getInstance();
//                System.out.println(Thread.currentThread().getName() + "------" + instance);

                Singleton_04 instance = Singleton_04.getInstance();
                System.out.println(Thread.currentThread().getName() + "------" + instance);
            }).start();
        }
    }

    @Test
    public void test03(){

    }

}
