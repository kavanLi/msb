package com.mashibing.composite.example02;

import java.util.ArrayList;

/**
 * Directory 容器对象,表示文件夹
 * @author spikeCong
 * @date 2022/10/7
 **/
public class Directory extends Entry {

    //文件的名字
    private String name;

    //文件夹和文件的集合
    private ArrayList<Entry> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }


    /**
     * 获取文件大小
     *      1.如果entry对象是file类型,则调用getSize方法获取文件大小
     *      2.如果entry对象是Directory类型,会继续调用子文件夹的getSize()方法,形成递归调用
     */
    @Override
    public int getSize() {

        int size = 0;

        //遍历获取文件大小
        for (Entry entry : directory) {
            size += entry.getSize();
        }

        return size;
    }

    @Override
    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    @Override
    public void printList(String prefix) {
        System.out.println("/" + this);
        for (Entry entry : directory) {
            entry.printList("/" + name);
        }
    }
}
