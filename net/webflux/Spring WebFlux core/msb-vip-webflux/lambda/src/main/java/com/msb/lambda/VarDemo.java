package com.msb.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Lambda表达式是实现某个接口的匿名类，那他引用变量和我们匿名类是相同的
 */
public class VarDemo {
    public static void main(String[] args) {
        String str = "欢迎您";
        Consumer<String> consumer = s -> System.out.println(s + str);
        consumer.accept("北京");
        // jdk8之前内部类引用外面的变量，这变量必须声明为final类型，
        // 那jdk8里面默认可以不写,他默认增加了final
        // 匿名类引用外面的变量必须是final
        // 因为我们java方法传参的形式，是传的值而不是引用
        List<String> list =new ArrayList<>();
        Consumer<String> consumer2 = s -> System.out.println(s + list);
        consumer.accept("北京");
    }
}
