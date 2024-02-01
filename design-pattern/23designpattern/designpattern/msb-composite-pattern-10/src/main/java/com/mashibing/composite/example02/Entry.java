package com.mashibing.composite.example02;

/**
 * Entry抽象类 (文件夹+文件)
 * @author spikeCong
 * @date 2022/10/7
 **/
public abstract class Entry {

    public abstract String getName(); //获取文件名

    public abstract int getSize(); //获取文件大小

    //添加文件或者文件夹方法
    public abstract Entry add(Entry entry);

    //显示指定目录下的所有文件的信息
    public abstract void printList(String prefix);

    @Override
    public String toString() {

        return getName() +"(" + getSize() +")";
    }
}
