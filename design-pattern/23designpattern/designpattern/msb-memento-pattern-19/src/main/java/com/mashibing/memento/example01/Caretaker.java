package com.mashibing.memento.example01;

/**
 * 负责人类-获取和保存备忘录对象
 * @author spikeCong
 * @date 2022/10/19
 **/
public class Caretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
