package com.example.demo.designpattern.factory.abstractfactory.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 21:26
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestAbstractFactory {

    /**
     * 抽象工厂模式是一种创建型设计模式，旨在提供一个创建一系列相关或相互依赖对象的接口，而无需指定它们的具体类。该模式通过抽象工厂类来创建一系列相关或相互依赖的对象，客户端代码无需关心对象的具体创建过程。
     *
     * 普通工厂模式与抽象工厂模式的区别:
     *
     * 普通工厂模式只能创建单个对象，而抽象工厂模式可以创建一系列相关或相互依赖的对象。
     * 普通工厂模式的工厂类通常是具体的，而抽象工厂模式的工厂类通常是抽象的。
     * 普通工厂模式的灵活性较低，而抽象工厂模式的灵活性较高。
     *
     * 抽象工厂模式的应用场景:
     *
     * 当系统需要创建一系列相关或相互依赖的对象时。
     * 当系统需要在不同的平台或不同的环境下使用不同的产品时。
     * 当系统需要提供一个产品类库，且所有产品的接口相同，客户端不依赖产品实例的创建细节和内部结构。
     *
     * 抽象工厂模式和普通工厂模式都是创建型设计模式，它们可以帮助我们提高代码的灵活性。抽象工厂模式比普通工厂模式更灵活，因为它可以创建一系列相关或相互依赖的对象。
     */
    public static void main(String[] args) {
        PizzaAbstractFactory factory = new ChicagoPizzaFactory();
        Pizza pizza = factory.createPizza();
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        factory = new NewYorkPizzaFactory();
        pizza = factory.createPizza();
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

}

interface PizzaAbstractFactory {
    Pizza createPizza();
}

class ChicagoPizzaFactory implements PizzaAbstractFactory {
    @Override
    public Pizza createPizza() {
        return new ChicagoPizza();
    }
}

class NewYorkPizzaFactory implements PizzaAbstractFactory {
    @Override
    public Pizza createPizza() {
        return new NewYorkPizza();
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
