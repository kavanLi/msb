package com.example.demo.designpattern.builder.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 21:40
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestBuilderTest1 {

    /**
     * 建造者模式：理解与代码案例
     * 建造者模式是一种创建型设计模式，旨在将对象的创建与对象的使用者分离，从而提高代码的灵活性。该模式通过建造者类来创建对象，客户端代码无需关心对象的具体创建过程。
     *
     * 理解建造者模式:
     *
     * 目标接口: 定义期望的接口，是客户端代码依赖的接口。
     * 建造者类: 负责将目标接口转换为适配者接口，并提供目标接口的实现。
     * 适配者类: 定义已有的接口，需要被适配的接口。
     * 建造者模式的优点:
     *
     * 提高代码的灵活性，允许在不修改目标接口的情况下改变适配者接口。
     * 提高代码的可扩展性，可以方便地添加新的适配器接口。
     * 降低代码的耦合度，使目标接口和适配者接口之间相互独立。
     * 建造者模式的应用场景:
     *
     * 当系统需要使用现有的类，而此类的接口不符合系统的需要。
     * 想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类，包括一些可能在将来引进的类一起工作，这些源类不一定有一致的接口。
     * 代码案例:
     *
     * 示例：创建披萨
     *
     * 假设我们需要创建不同类型的披萨，例如芝加哥披萨和纽约披萨。可以使用建造者模式来实现这个功能，如下代码所示：
     */
    // 主类
    public static void main(String[] args) {
        // 创建奔驰汽车
        CarBuilder benzCarBuilder = new BenzCarBuilder();
        Director director = new Director(benzCarBuilder);
        director.constructCar();
        Car benzCar = director.getCar();
        benzCar.start();
        benzCar.run();
        benzCar.stop();

        // 创建宝马汽车
        CarBuilder bmwCarBuilder = new BMWCarBuilder();
        director = new Director(bmwCarBuilder);
        director.constructCar();
        Car bmwCar = director.getCar();
        bmwCar.start();
        bmwCar.run();
        bmwCar.stop();
    }
}

// 目标接口：汽车
interface Car {
    void start();
    void run();
    void stop();
}

// 建造者类：抽象汽车建造者
abstract class CarBuilder {
    protected Car car;

    public abstract void buildEngine();
    public abstract void buildBody();
    public abstract void buildWheels();
    public abstract Car getCar();
}

// 具体建造者类：奔驰汽车建造者
class BenzCarBuilder extends CarBuilder {
    public BenzCarBuilder() {
        car = new BenzCar();
    }

    @Override
    public void buildEngine() {
        System.out.println("安装奔驰汽车引擎");
    }

    @Override
    public void buildBody() {
        System.out.println("安装奔驰汽车车身");
    }

    @Override
    public void buildWheels() {
        System.out.println("安装奔驰汽车车轮");
    }

    @Override
    public Car getCar() {
        return car;
    }
}

// 具体建造者类：宝马汽车建造者
class BMWCarBuilder extends CarBuilder {
    public BMWCarBuilder() {
        car = new BMWCar();
    }

    @Override
    public void buildEngine() {
        System.out.println("安装宝马汽车引擎");
    }

    @Override
    public void buildBody() {
        System.out.println("安装宝马汽车车身");
    }

    @Override
    public void buildWheels() {
        System.out.println("安装宝马汽车车轮");
    }

    @Override
    public Car getCar() {
        return car;
    }
}

// 奔驰汽车
class BenzCar implements Car {
    @Override
    public void start() {
        System.out.println("奔驰汽车启动");
    }

    @Override
    public void run() {
        System.out.println("奔驰汽车行驶");
    }

    @Override
    public void stop() {
        System.out.println("奔驰汽车停止");
    }
}

// 宝马汽车
class BMWCar implements Car {
    @Override
    public void start() {
        System.out.println("宝马汽车启动");
    }

    @Override
    public void run() {
        System.out.println("宝马汽车行驶");
    }

    @Override
    public void stop() {
        System.out.println("宝马汽车停止");
    }
}

// 导演类：负责组装汽车
class Director {
    private CarBuilder carBuilder;

    public Director(CarBuilder carBuilder) {
        this.carBuilder = carBuilder;
    }

    public void constructCar() {
        carBuilder.buildEngine();
        carBuilder.buildBody();
        carBuilder.buildWheels();
    }

    public Car getCar() {
        return carBuilder.getCar();
    }
}



