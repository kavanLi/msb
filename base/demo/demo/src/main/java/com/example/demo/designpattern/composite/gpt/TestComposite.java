package com.example.demo.designpattern.composite.gpt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 10:13
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestComposite {
/**
 *组合模式（Composite Pattern）
 * 理解:
 *
 * 组合模式是一种结构型设计模式，它将对象组合成树形结构以表示“部分-整体”的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 *
 * 关键点:
 *
 * 将对象组合成树形结构
 * 对单个对象和组合对象使用一致的接口
 * 提高代码的层次性和可扩展性
 */
    // 使用示例
    public static void main(String[] args) {
        Component leaf1 = new Leaf();
        Component leaf2 = new Leaf();
        Component composite = new Composite();

        composite.add(leaf1);
        composite.add(leaf2);

        composite.operation();
    }

}

// 抽象构件
interface Component {
    void add(Component component);
    void remove(Component component);
    void operation();
}

// 叶子构件
class Leaf implements Component {
    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void operation() {
        // 叶子构件的具体操作
    }
}

// 容器构件
class Composite implements Component {
    private List <Component> components = new ArrayList <>();

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void remove(Component component) {
        components.remove(component);
    }

    @Override
    public void operation() {
        // 容器构件的具体操作
        for (Component component : components) {
            component.operation();
        }
    }
}
