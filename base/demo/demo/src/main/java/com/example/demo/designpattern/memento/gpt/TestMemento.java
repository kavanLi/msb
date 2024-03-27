package com.example.demo.designpattern.memento.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-07 14:39
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestMemento {
/**
 *备忘录模式（Memento Pattern）
 * 备忘录模式是一种行为设计模式，它允许在不暴露对象内部状态的情况下保存和恢复对象之前的状态。
 *
 * 模式结构：
 *
 * 发起者（Originator）： 拥有需要保存的状态的对象。
 * 备忘录（Memento）： 存储发起者状态的对象。
 * 管理员（Caretaker）： 负责存储和恢复发起者状态的备忘录对象。
 * 模式实现：
 *
 * 发起者创建一个备忘录对象，将自己的内部状态存储到备忘录中。
 * 管理员将备忘录对象存储起来。
 * 当需要恢复发起者状态时，管理员将备忘录对象传递给发起者。
 * 发起者从备忘录对象中恢复自己的内部状态。
 * 模式优点：
 *
 * 可以将对象的内部状态与对象的表示分离。
 * 可以方便地恢复对象的之前的状态。
 * 可以实现对象的撤销/重做功能。
 * 模式缺点：
 *
 * 需要额外的存储空间来存储备忘录对象。
 * 增加了代码的复杂度。
 */
public static void main(String[] args) {

    Originator originator = new Originator();
    originator.setState("初始状态");

    Caretaker caretaker = new Caretaker();
    caretaker.setMemento(originator.createMemento());

    originator.setState("新的状态");

    System.out.println("当前状态：" + originator.getState());

    originator.restoreMemento(caretaker.getMemento());

    System.out.println("恢复后的状态：" + originator.getState());

}
}

class Originator {

    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento createMemento() {
        return new Memento(state);
    }

    public void restoreMemento(Memento memento) {
        this.state = memento.getState();
    }

}

class Memento {

    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}

class Caretaker {

    private Memento memento;

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    public Memento getMemento() {
        return memento;
    }

}
