package com.example.demo.designpattern.visitor.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-13 10:07
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestVisitor {
/**
 * 访问者模式简介
 * 访问者模式是一种行为型设计模式，它将作用于某种数据结构中的各元素的操作分离出来封装成独立的类，使其在不改变数据结构的前提下可以添加作用于这些元素的新的操作，为数据结构中的每个元素提供多种访问方式，它将对数据的操作与数据结构进行分离。
 *
 * 访问者模式的关键组件有三个:
 *
 * 访问者 (Visitor): 定义对数据结构中各个元素的操作接口，可以根据需要定义不同的访问操作。
 * 元素 (Element): 定义数据结构的接口，通常包括一个接受访问者对象的方法。
 * 具体元素 (ConcreteElement): 为具体元素实现accept方法
 * 访问者模式的优点:
 *
 * 提高代码的灵活性: 可以将新的操作添加到访问者类中，而无需修改数据结构。
 * 降低代码的耦合: 访问者和元素之间通过接口进行交互，降低了耦合。
 * 提高代码的可扩展性: 可以根据需要扩展数据结构，而无需修改访问者类。
 *
 * 访问者模式的应用
 * 访问者模式是一种非常有用的设计模式，可以应用于各种场景，例如：
 *
 * 编译器: 在编译器中，访问者模式可以用于实现不同的代码分析。
 * 解释器: 在解释器中，访问者模式可以用于实现对不同语法元素的解释。
 * XML 解析: 在 XML 解析中，访问者模式可以用于实现对不同 XML 元素的处理。
 */
    // 测试
    public static void main(String[] args) {
        ConcreteElementA elementA = new ConcreteElementA();
        ConcreteElementB elementB = new ConcreteElementB();

        Visitor visitorA = new ConcreteVisitorA();
        visitorA.visit(elementA);
        visitorA.visit(elementB);

        Visitor visitorB = new ConcreteVisitorB();
        visitorB.visit(elementA);
        visitorB.visit(elementB);
    }

}

// 定义访问者接口
interface Visitor {
    void visit(ConcreteElementA elementA);
    void visit(ConcreteElementB elementB);
}

// 定义元素接口
interface Element {
    void accept(Visitor visitor);
}

// 定义具体元素
class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void operationA() {
        // 具体操作
    }
}

class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void operationB() {
        // 具体操作
    }
}

// 定义具体访问者
class ConcreteVisitorA implements Visitor {
    @Override
    public void visit(ConcreteElementA elementA) {
        elementA.operationA();
    }

    @Override
    public void visit(ConcreteElementB elementB) {
        // 具体操作
    }
}

class ConcreteVisitorB implements Visitor {
    @Override
    public void visit(ConcreteElementA elementA) {
        // 具体操作
    }

    @Override
    public void visit(ConcreteElementB elementB) {
        elementB.operationB();
    }
}