package com.msb.stream;

import java.util.Random;
import java.util.stream.Stream;

public class StreamDemo3 {
    public static void main(String[] args) {
        String str = "my name is Jack";
        // 把每个单词的长度调用出来
      //  Stream.of(str.split(" ")).map(s ->s.length()).forEach(System.out::println);
//        Stream.of(str.split(" ")).filter(s -> s.length() > 2)
//                .map(s -> s.length()).forEach(System.out::println);
        // flatMap A -> B属性(是个集合),最终得到所有的A元素里面的所有B属性集合
        // intStream/longStream 并不是Stream的子类，所以要进行装箱
        Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed() ).forEach(System.out::println);
 Stream.of(str.split(" ")).flatMap(s -> s.chars().boxed()).forEach(
                i -> System.out.println((char)i.intValue()));
//        // peek 用于debug,是中间操作和forEach 是终止操作
        System.out.println("=============peek===============");
        Stream.of(str.split(" ")).peek(System.out::println).forEach(System.out::println);
//
//        // limit 使用，主要用于无限流
        // 1、我们不做限制的看看他是否出现异常,这里没有异常，好像一直不会结束
        new Random().ints().forEach(System.out::println);
        new Random().ints().filter(i -> i > 100 && i < 1000).limit(3)
                .forEach(System.out::println);

        // 大家主要我们intStream longStream这些并不是Stream的子类，他和Stream操作的时候经常要装箱
     }
}
