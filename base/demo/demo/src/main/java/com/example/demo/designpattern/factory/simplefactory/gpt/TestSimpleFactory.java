package com.example.demo.designpattern.factory.simplefactory.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 21:24
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestSimpleFactory {


    /**
     * 普通工厂模式是一种创建型设计模式，旨在将对象的创建与对象的使用者分离，从而提高代码的灵活性。该模式通过工厂类来创建对象，客户端代码无需关心对象的具体创建过程。
     *
     * 普通工厂模式与抽象工厂模式的区别:
     *
     * 普通工厂模式只能创建单个对象，而抽象工厂模式可以创建一系列相关或相互依赖的对象。
     * 普通工厂模式的工厂类通常是具体的，而抽象工厂模式的工厂类通常是抽象的。
     * 普通工厂模式的灵活性较低，而抽象工厂模式的灵活性较高。
     * 普通工厂模式的应用场景:
     *
     * 当系统需要创建的对象类型比较固定，并且不需要频繁地改变时。
     * 当系统需要将对象的创建与对象的使用者分离，以便提高代码的灵活性。
     *
     * 代码案例:
     *
     * 示例：创建披萨
     *
     * 假设我们需要创建不同类型的披萨，例如芝加哥披萨和纽约披萨。可以使用普通工厂模式和抽象工厂模式来实现这个功能。
     *
     * @param args
     */
    public static void main(String[] args) {
        Pizza pizza = PizzaFactory.createPizza("Chicago");
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

}

class PizzaFactory {
    public static Pizza createPizza(String type) {
        if ("Chicago".equals(type)) {
            return new ChicagoPizza();
        } else if ("NewYork".equals(type)) {
            return new NewYorkPizza();
        } else {
            return null;
        }
    }
}

abstract class Pizza {
    public abstract void prepare();
    public abstract void bake();
    public abstract void cut();
    public abstract void box();
}

class ChicagoPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("准备芝加哥披萨");
    }

    @Override
    public void bake() {
        System.out.println("烘烤芝加哥披萨");
    }

    @Override
    public void cut() {
        System.out.println("切割芝加哥披萨");
    }

    @Override
    public void box() {
        System.out.println("打包芝加哥披萨");
    }
}

class NewYorkPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("准备纽约披萨");
    }

    @Override
    public void bake() {
        System.out.println("烘烤纽约披萨");
    }

    @Override
    public void cut() {
        System.out.println("切割纽约披萨");
    }

    @Override
    public void box() {
        System.out.println("打包纽约披萨");
    }
}