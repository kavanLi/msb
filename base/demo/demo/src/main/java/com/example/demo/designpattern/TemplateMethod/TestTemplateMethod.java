package com.example.demo.designpattern.TemplateMethod;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-13 10:03
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestTemplateMethod {
/**
 * 模板方法模式简介
 * 模板方法模式是一种设计模式，它定义了一个算法的框架，允许子类在不修改结构的情况下重写算法的特定步骤。
 *
 * 模板方法模式的关键组件有三个:
 *
 * 抽象类 (Abstract Class): 定义算法的框架，并提供一些默认的实现。
 * 具体类 (Concrete Class): 继承抽象类，并重写算法中特定步骤的实现。
 * 客户端 (Client): 使用抽象类或具体类来执行算法。
 * 模板方法模式的优点:
 *
 * 提高代码的可读性和可维护性: 将算法的框架与具体的实现分离，使代码更加清晰易懂。
 * 提高代码的可扩展性: 可以根据需要扩展算法，而无需修改现有代码。
 * 降低耦合: 抽象类和具体类之间通过继承关系进行关联，降低了耦合。
 *
 * 模板方法模式的应用
 * 模板方法模式是一种非常有用的设计模式，可以应用于各种场景，例如：
 *
 * GUI 编程: 在 GUI 编程中，模板方法模式可以用于实现各种控件的通用行为。
 * 数据访问: 在数据访问中，模板方法模式可以用于实现对不同数据库的访问。
 * 日志记录: 在日志记录中，模板方法模式可以用于实现不同日志格式的输出。
 */
    // 测试
    public static void main(String[] args) {
        AbstractClass ac1 = new ConcreteClassA();
        ac1.templateMethod();

        AbstractClass ac2 = new ConcreteClassB();
        ac2.templateMethod();
    }

}

// 定义抽象类
abstract class AbstractClass {
    public void templateMethod() {
        this.step1();
        this.step2();
        this.step3();
    }

    protected abstract void step1();

    protected abstract void step2();

    protected void step3() {
        // 默认实现
        System.out.println("step3");
    }
}

// 定义具体类
class ConcreteClassA extends AbstractClass {
    @Override
    protected void step1() {
        System.out.println("step1 in ConcreteClassA");
    }

    @Override
    protected void step2() {
        System.out.println("step2 in ConcreteClassA");
    }
}

class ConcreteClassB extends AbstractClass {
    @Override
    protected void step1() {
        System.out.println("step1 in ConcreteClassB");
    }

    @Override
    protected void step2() {
        System.out.println("step2 in ConcreteClassB");
    }

    @Override
    protected void step3() {
        System.out.println("step3 in ConcreteClassB");
    }
}