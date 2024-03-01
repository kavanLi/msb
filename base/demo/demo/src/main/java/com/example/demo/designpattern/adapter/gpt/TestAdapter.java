package com.example.demo.designpattern.adapter.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-29 21:19
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestAdapter {

    /**
     * 适配器模式：理解与代码案例
     * 适配器模式是一种结构型设计模式，旨在将两个不兼容的接口进行转换，使它们可以相互协作。该模式通过组合关系来代替继承关系，从而提高代码的灵活性性和可扩展性。
     *
     * 理解适配器模式:
     *
     * 目标接口: 定义期望的接口，是客户端代码依赖的接口。
     * 适配器类: 负责将目标接口转换为适配者接口，并提供目标接口的实现。
     * 适配者类: 定义已有的接口，需要被适配的接口。
     * 适配器模式的优点:
     *
     * 提高代码的灵活性，允许在不修改目标接口的情况下改变适配者接口。
     * 提高代码的可扩展性，可以方便地添加新的适配器接口。
     * 降低代码的耦合度，使目标接口和适配者接口之间相互独立。
     * 适配器模式的应用场景:
     *
     * 当系统需要使用现有的类，而此类的接口不符合系统的需要。
     * 想要建立一个可以重复使用的类，用于与一些彼此之间没有太大关联的一些类，包括一些可能在将来引进的类一起工作，这些源类不一定有一致的接口。
     * 代码案例:
     *
     * 示例：电源适配器
     *
     * 假设我们需要使用 220V 电压的电器，但在我们所在的地区只有 110V 电压。可以使用适配器模式来解决这个问题，如下代码所示：
     * @param args
     */
    public static void main(String[] args) {
        Power110V power110V = new Power110V();
        Adapter adapter = new Adapter(power110V);
        adapter.use(); // 输出：使用适配器将 110V 电压转换为 220V 电压
        // 使用 110V 电压
    }

}

// 目标接口：220V 电压
interface Power220V {
    void use();
}

// 适配器类：将 110V 电压转换为 220V 电压
class Adapter implements Power220V {
    private Power110V power110V;

    public Adapter(Power110V power110V) {
        this.power110V = power110V;
    }

    @Override
    public void use() {
        System.out.println("使用适配器将 110V 电压转换为 220V 电压");
        power110V.use();
    }
}

// 适配者类：110V 电压
class Power110V {
    public void use() {
        System.out.println("使用 110V 电压");
    }
}
