package com.example.demo.designpattern.observer.gpt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-12 10:00
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestObserver {
/**
 *观察者模式简介
 * 观察者模式是一种设计模式，它定义了一种一对多的依赖关系，其中一个对象（称为被观察者）的状态发生变化时，所有依赖它的对象（称为观察者）都会得到通知。
 *
 * 观察者模式的优点:
 *
 * 松耦合: 观察者模式可以将观察者和被观察者解耦，使它们可以独立变化。
 * 易于扩展: 观察者模式很容易扩展，可以添加新的观察者或被观察者，而无需修改现有代码。
 * 灵活性: 观察者模式可以用于各种场景，例如事件处理、数据更新、消息队列等。
 *
 * 观察者模式是一种非常有用的设计模式，可以应用于各种场景，例如：
 *
 * GUI 编程: 在 GUI 编程中，观察者模式可以用于监听按钮点击、文本框输入等事件。
 * 事件处理: 在事件处理中，观察者模式可以用于监听各种事件，例如文件更改、网络连接断开等。
 * 数据更新: 在数据更新中，观察者模式可以用于监听数据的变化，并及时更新相关的 UI 界面。
 * 消息队列: 在消息队列中，观察者模式可以用于监听消息队列的变化，并及时处理新消息。
 * 观察者模式可以帮助我们开发更健壮、更易于维护的软件。
 */
    // 测试
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver1();
        Observer observer2 = new ConcreteObserver2();

        subject.addObserver(observer1);
        subject.addObserver(observer2);

        // 被观察者状态发生变化
        subject.changeState();
    }

}

// 定义被观察者接口
interface Subject {
    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();

    void changeState();
}

// 定义具体被观察者
class ConcreteSubject implements Subject {
    private List <Observer> observers = new ArrayList <>();

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    // 被观察者状态发生变化
    @Override
    public void changeState() {
        // ...
        notifyObservers();
    }
}

// 定义观察者接口
interface Observer {
    void update();
}

// 定义具体观察者
class ConcreteObserver1 implements Observer {
    @Override
    public void update() {
        // 观察者逻辑
    }
}

class ConcreteObserver2 implements Observer {
    @Override
    public void update() {
        // 观察者逻辑
    }
}