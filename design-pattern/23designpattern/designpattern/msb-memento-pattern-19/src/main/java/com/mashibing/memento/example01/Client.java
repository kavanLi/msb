package com.mashibing.memento.example01;

/**
 * @author spikeCong
 * @date 2022/10/19
 **/
public class Client {

    public static void main(String[] args) {

        //创建发起人对象
        Originator o1 = new Originator();
        o1.setId("1");
        o1.setName("spike");
        o1.setPhone("13522777722");
        System.out.println("========"+o1);

        //创建负责人对象
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(o1.createMemento());

        //修改
        o1.setName("update");
        System.out.println("========" + o1);

        //从负责人对象中获取备忘录对象,实现恢复操作
        o1.restoreMemento(caretaker.getMemento());
        System.out.println("========" + o1);
    }
}
