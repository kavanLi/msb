package com.example.demo.designpattern.iterator.gpt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-06 16:07
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestIterator {


    /**
     * 设计模式Iterator是一种行为型设计模式，它提供了一种方法来访问一个容器对象中各个元素，而又不需暴露该对象的内部细节。
     *
     * Iterator模式的结构
     *
     * Iterator模式主要由以下角色组成：
     *
     * Iterator：迭代器角色负责定义访问和遍历元素的接口。
     * ConcreteIterator：具体迭代器角色要实现迭代器接口，并要记录遍历中的当前位置。
     * Container：容器角色负责提供创建具体迭代器角色的接口。
     * ConcreteContainer：具体容器角色实现创建具体迭代器角色的接口——这个具体迭代器角色于该容器的结构相关。
     */
    public static void main(String[] args) {
        Container container = new Container();
        container.add("A");
        container.add("B");
        container.add("C");

        Iterator iterator = container.getIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

interface Iterator {
    Object next();
    boolean hasNext();
}

class ConcreteIterator implements Iterator {
    private List<Object> list;
    private int currentIndex;

    public ConcreteIterator(List<Object> list) {
        this.list = list;
        currentIndex = 0;
    }

    @Override
    public Object next() {
        return list.get(currentIndex++);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < list.size();
    }
}

class Container {
    private List<Object> list;

    public Container() {
        list = new ArrayList<>();
    }

    public void add(Object object) {
        list.add(object);
    }

    public Iterator getIterator() {
        return new ConcreteIterator(list);
    }
}







