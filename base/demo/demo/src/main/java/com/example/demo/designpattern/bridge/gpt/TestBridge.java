package com.example.demo.designpattern.bridge.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 21:07
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestBridge {

    /**
     * 桥接模式：理解与代码案例
     * 桥接模式是一种结构型设计模式，旨在将抽象与实现分离，使它们可以独立变化。该模式通过组合关系来代替继承关系，从而提高代码的灵活性和可扩展性。
     *
     * 理解桥接模式:
     *
     * 抽象: 定义抽象接口，描述业务逻辑的核心功能。
     * 实现: 定义具体实现，提供抽象接口的具体实现。
     * 桥接: 连接抽象和实现，将两者组合在一起。
     * 桥接模式的优点:
     *
     * 提高代码的灵活性，允许在不修改抽象的情况下改变实现。
     * 提高代码的可扩展性，可以方便地添加新的抽象和实现。
     * 降低代码的耦合度，使抽象和实现之间相互独立。
     * 桥接模式的应用场景:
     *
     * 当系统存在两个独立变化的维度，需要对它们进行独立扩展时。
     * 不希望使用继承关系，或因为多层继承导致系统类的个数剧增。
     * 需要在抽象和实现之间增加更多的灵活性。
     * 代码案例:
     *
     * 示例：图像绘制
     *
     * 假设我们需要绘制不同形状的图像，并可以为这些图像填充不同的颜色。使用桥接模式可以将形状和颜色这两个独立变化的维度进行解耦，如下代码所示：
     * @param args
     */
    public static void main(String[] args) {
        Bridge bridge1 = new Bridge(new Rectangle(), new Red());
        bridge1.draw(); // 输出：绘制矩形，填充红色

        Bridge bridge2 = new Bridge(new Circle(), new Green());
        bridge2.draw(); // 输出：绘制圆形，填充绿色
    }

}

// 抽象：形状接口
interface Shape {
    void draw();
}

// 实现：具体形状
class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制矩形");
    }
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }
}

// 抽象：颜色接口
interface Color {
    void fill();
}

// 实现：具体颜色
class Red implements Color {
    @Override
    public void fill() {
        System.out.println("填充红色");
    }
}

class Green implements Color {
    @Override
    public void fill() {
        System.out.println("填充绿色");
    }
}

// 桥接：将形状和颜色组合在一起
class Bridge {
    private Shape shape;
    private Color color;

    public Bridge(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Bridge() {
    }

    public void draw() {
        shape.draw();
        color.fill();
    }
}