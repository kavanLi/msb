package com.example.demo.designpattern.prototype.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-12 10:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestPrototype {
    /**
     * 原型模式简介
     * 原型模式是一种设计模式，它通过克隆一个原型对象来创建新的对象。原型模式可以用于减少对象的创建成本，提高对象的创建效率。
     *
     * 原型模式的优点:
     *
     * 减少对象的创建成本: 原型模式可以避免重复创建相同或相似的对象，从而减少对象的创建成本。
     * 提高对象的创建效率: 原型模式可以利用克隆技术快速创建新的对象，从而提高对象的创建效率。
     * 增加对象的灵活性: 原型模式可以动态地创建新的对象，从而增加对象的灵活性。
     *
     * 原型模式的应用
     * 原型模式是一种非常有用的设计模式，可以应用于各种场景，例如：
     *
     * GUI 编程: 在 GUI 编程中，原型模式可以用于创建各种按钮、文本框等控件。
     * 文档编辑: 在文档编辑中，原型模式可以用于创建各种文档模板。
     * 游戏开发: 在游戏开发中，原型模式可以用于创建各种游戏角色、道具等。
     */
    // 测试
    public static void main(String[] args) {
        ConcretePrototype prototype = new ConcretePrototype("张三", 20);

        // 克隆原型对象
        ConcretePrototype clone = (ConcretePrototype) prototype.clone();

        System.out.println(prototype.getName() + " " + prototype.getAge());
        System.out.println(clone.getName() + " " + clone.getAge());
    }
}

// 定义原型接口
interface Prototype {
    Prototype clone();
}

// 定义具体原型
class ConcretePrototype implements Prototype {
    private String name;
    private int age;

    public ConcretePrototype(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public Prototype clone() {
        return new ConcretePrototype(name, age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}


