package com.mashibing.example01;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author spikeCong
 * @date 2022/9/21
 **/
public class TestPrototype {

    @Test
    public void test01() throws CloneNotSupportedException {

        ConcretePrototype c1 = new ConcretePrototype();
        ConcretePrototype c2 = c1.clone();

        System.out.println("对象c1和对象c2是同一个对象吗?" + (c1 == c2));
    }

    @Test
    public void test02() throws CloneNotSupportedException {

        ConcretePrototype c1 = new ConcretePrototype();
        Person p1 = new Person("峰哥");
        c1.setPerson(p1);

        //复制c1
        ConcretePrototype c2 = c1.clone();
        Person p2 = c2.getPerson();
        p2.setName("凡哥");

        c1.show();
        c2.show();
        System.out.println("对象p1和对象p2是同一个对象吗?" + (p1 == p2));
    }

    @Test
    public void test03() throws Exception {

        ConcretePrototype c1 = new ConcretePrototype();
        Person p1 = new Person("峰哥");
        c1.setPerson(p1);

        //创建对象序列化输出流
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("a.txt"));

        //将c1对象写到文件
        oos.writeObject(c1);
        oos.close();

        //创建对象序列化输入流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("a.txt"));
        //读取对象
        ConcretePrototype c2 = (ConcretePrototype) ois.readObject();
        Person p2 = c2.getPerson();
        p2.setName("凡哥");

        c1.show();
        c2.show();
        System.out.println("对象p1和对象p2是同一个对象吗?" + (p1 == p2));
    }
}
