package com.example.demo.designpattern.strategy.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-13 09:53
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestStrategy {
/**
 * 策略模式简介
 * 策略模式是一种设计模式，它定义了一系列算法，并将每个算法封装成一个独立的对象。策略模式可以使算法与使用算法的代码分离，从而提高代码的灵活性和可维护性。
 *
 * 策略模式的关键组件有三个:
 *
 * 策略 (Strategy): 定义一个算法的接口。
 * 具体策略 (Concrete Strategy): 实现 Strategy 接口，并定义具体的算法。
 * 上下文 (Context): 维护当前策略并封装策略转换逻辑。
 * 策略模式的优点:
 *
 * 提高代码的灵活性和可维护性: 将算法与使用算法的代码分离，使代码更加清晰易懂。
 * 提高代码的可扩展性: 可以根据需要添加新的策略，而无需修改现有代码。
 * 降低耦合: 策略之间通过上下文对象进行交互，降低了策略之间的耦合。
 *
 * 策略模式的应用
 * 策略模式是一种非常有用的设计模式，可以应用于各种场景，例如：
 *
 * 游戏开发: 在游戏开发中，策略模式可以用于实现不同的游戏玩法。
 * 图像处理: 在图像处理中，策略模式可以用于实现不同的图像滤镜。
 * 排序算法: 在排序算法中，策略模式可以用于实现不同的排序策略。
 */
    // 测试
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStrategyA());
        System.out.println(context.doOperation(1, 2)); // 3

        context.setStrategy(new ConcreteStrategyB());
        System.out.println(context.doOperation(1, 2)); // -1
    }

}

// 定义策略接口
interface Strategy {
    int doOperation(int num1, int num2);
}

// 定义具体策略
class ConcreteStrategyA implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}

class ConcreteStrategyB implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}

// 定义上下文
class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int doOperation(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}