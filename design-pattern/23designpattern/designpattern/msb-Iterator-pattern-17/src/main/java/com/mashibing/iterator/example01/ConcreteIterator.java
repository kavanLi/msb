package com.mashibing.iterator.example01;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * 具体的迭代器
 * @author spikeCong
 * @date 2022/10/18
 **/
public class ConcreteIterator<E> implements Iterator<E> {

    private int cursor; //游标

    private ArrayList<E> arrayList;  //容器

    public ConcreteIterator(ArrayList<E> arrayList) {
        this.cursor = 0;
        this.arrayList = arrayList;
    }

    @Override
    public boolean hasNext() {
        return cursor != arrayList.size();
    }

    @Override
    public void next() {
        cursor++;
    }

    @Override
    public E currentItem() {
        if(cursor >= arrayList.size()){
            throw new NoSuchElementException();
        }
        return arrayList.get(cursor);
    }
}
