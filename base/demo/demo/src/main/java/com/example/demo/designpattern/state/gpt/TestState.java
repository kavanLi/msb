package com.example.demo.designpattern.state.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-12 10:33
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestState {
/**
 *状态模式简介
 * 状态模式是一种设计模式，它允许对象在其内部状态改变时改变它的行为。状态模式基于对象的内部状态而改变其行为，看起来好像修改了对象的类。
 *
 * 状态模式的关键组件有三个:
 *
 * 上下文 (Context): 负责维护当前状态并封装状态转换逻辑。
 * 状态 (State): 定义一个状态的接口，并封装该状态下的行为。
 * 具体状态 (Concrete State): 实现 State 接口，并定义具体的行为。
 * 状态模式的优点:
 *
 * 提高代码的可读性和可维护性: 将状态逻辑与对象的行为分离，使代码更加清晰易懂。
 * 提高代码的灵活性: 可以根据需要添加新的状态，而无需修改现有代码。
 * 降低耦合: 状态之间通过上下文对象进行交互，降低了状态之间的耦合。
 *
 * 状态模式的应用
 * 状态模式是一种非常有用的设计模式，可以应用于各种场景，例如：
 *
 * GUI 编程: 在 GUI 编程中，状态模式可以用于实现按钮的各种状态，例如按下、抬起、禁用等。
 * 游戏开发: 在游戏开发中，状态模式可以用于实现角色的各种状态，例如行走、攻击、死亡等。
 * 网络通信: 在网络通信中，状态模式可以用于实现连接的各种状态，例如连接中、已连接、断开等。
 * 状态模式可以帮助我们开发更健壮、更易于维护的软件。
 */
    // 测试
    public static void main(String[] args) {
        Context context = new Context(new ConcreteStateA());
        context.doSomething();

        context.setState(new ConcreteStateB());
        context.doSomething();
    }

}

// 定义状态接口
interface State {
    void doSomething();
}

// 定义具体状态
class ConcreteStateA implements State {
    @Override
    public void doSomething() {
        // 状态 A 的行为
    }
}

class ConcreteStateB implements State {
    @Override
    public void doSomething() {
        // 状态 B 的行为
    }
}

// 定义上下文
class Context {
    private State state;

    public Context(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void doSomething() {
        state.doSomething();
    }
}
