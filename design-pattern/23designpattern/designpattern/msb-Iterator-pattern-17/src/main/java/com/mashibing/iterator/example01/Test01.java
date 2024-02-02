package com.mashibing.iterator.example01;

import java.util.ArrayList;

/**
 * @author spikeCong
 * @date 2022/10/18
 **/
public class Test01 {

    public static void main(String[] args) {

        ArrayList<String> names = new ArrayList<>();
        names.add("lisi");
        names.add("zhangsan");
        names.add("wangwu");

//        Iterator<String> iterator = new ConcreteIterator<>(names);
//        while(iterator.hasNext()){
//            System.out.println(iterator.currentItem());
//            iterator.next();
//        }

        java.util.Iterator<String> iterator1 = names.iterator();
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
    }
}
