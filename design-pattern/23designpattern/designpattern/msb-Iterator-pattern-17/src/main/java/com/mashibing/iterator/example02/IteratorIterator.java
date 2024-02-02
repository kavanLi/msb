package com.mashibing.iterator.example02;

/**
 * 抽象迭代器
 * @author spikeCong
 * @date 2022/10/18
 **/
public interface IteratorIterator<E> {

    void reset();  //重置为第一个元素

    E next(); //获取下一个元素

    E currentItem();  //检索当前元素

    boolean hasNext(); //判断集合中是否还有下一个元素
}
