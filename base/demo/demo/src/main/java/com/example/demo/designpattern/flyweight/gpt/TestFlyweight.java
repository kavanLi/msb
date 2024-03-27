package com.example.demo.designpattern.flyweight.gpt;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 15:50
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestFlyweight {
/**
 * 享元模式简介
 * 定义:
 *
 * 享元，顾名思义，就是共享的单元。
 *
 * 享元模式是一种设计模式，它使用共享对象来尽可能减少内存使用量，并尽可能多地共享相似对象之间的信息。当大量对象近乎重复地存在，因而使用大量内存时，此模式适用。通常情况下，对象的某些状态是可以共享的。常见做法是把它们放在外部数据结构中，当需要使用时再将它们传递给享元。
 *
 * 特点:
 *
 * 减少内存使用量，提高性能。
 * 将对象的内部状态和外部状态分离，提高代码的灵活性。
 * 降低对象的复杂度，提高代码的可维护性。
 * 应用场景:
 *
 * 字符处理
 * 图形图像
 * 数据库连接池
 * 缓存
 * 代码案例:
 *
 * 示例：字符处理
 *
 * 假设我们有一个文本编辑器，需要显示大量的文本。我们可以使用享元模式来减少内存使用量。
 */
    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        Flyweight flyweight1 = factory.getFlyweight('A');
        flyweight1.display(10, 20);

        Flyweight flyweight2 = factory.getFlyweight('B');
        flyweight2.display(30, 40);

        Flyweight flyweight3 = factory.getFlyweight('A');
        flyweight3.display(50, 60);

        System.out.println("享元池大小：" + factory.flyweights.size());
    }

}

interface Flyweight {
    void display(int x, int y);
}

class CharFlyweight implements Flyweight {

    private char character;

    public CharFlyweight(char character) {
        this.character = character;
    }

    @Override
    public void display(int x, int y) {
        System.out.println("显示字符：" + character + "，坐标：" + x + "," + y);
    }
}

class FlyweightFactory {

    public Map <Character, Flyweight> flyweights = new HashMap <>();

    public Flyweight getFlyweight(char character) {
        Flyweight flyweight = flyweights.get(character);
        if (flyweight == null) {
            flyweight = new CharFlyweight(character);
            flyweights.put(character, flyweight);
        }
        return flyweight;
    }
}