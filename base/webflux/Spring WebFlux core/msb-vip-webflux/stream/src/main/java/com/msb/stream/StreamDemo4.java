package com.msb.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo4 {
    public static void main(String[] args) {
//        String str = "my name is jack";
//
//        // 使用并行流,我们打印一下发现是乱的
//        str.chars().parallel().forEach(i -> System.out.print((char)i));
//        System.out.println();
////        // 使用 forEachOrdered保证顺序
//        str.chars().parallel().forEachOrdered(i -> System.out.print((char)i));
//        //第二行：collect/toArray我们可以看到他是一个收集的作用，他可以转成数组或者集合
//        // 收集到list
//        List<String> list = Stream.of(str.split(" ")).collect(Collectors.toList());
//        System.out.println(list);
////
////        // 第三个是reduce操作，reduce就是减少的意思，他就是将流合成一个数据，
////        //使用reduce拼接字符串
//          // 这里返回一个optional 这也是新jdk8添加的， optional是选项的意思，避免自己调用一些空判断，所以我们一般是这样使用
//        Optional<String> letters = Stream.of(str.split(" "))
//                .reduce((s1, s2) -> s1 + "|" + s2);
//        System.out.println(letters.orElse(""));
//        // 带初始化值的reduce
//        String reduce = Stream.of(str.split(" "))
//                .reduce("", (s1, s2) -> s1 + "|" + s2);
//        System.out.println(reduce);
//
//        //这就是字符串总长度
//       Integer length = Stream.of(str.split(" ")).map(s ->s.length())
//                .reduce(0, (s1, s2) -> s1 +  s2);
//        System.out.println(length);
//        // 使用max
//        Optional<String> max = Stream.of(str.split(" ")).max((s1, s2) -> s1.length() - s2.length());
//        System.out.println(max.get());
//        // 使用findFirst短路操作
//        OptionalInt first = new Random().ints().findAny();
//        System.out.println(first.getAsInt());

        // allMatch
        Student stu1 = new Student( 19, "张三");
        Student stu2 = new Student( 23, "李四");
        Student stu3 = new Student( 28, "王五");
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
        //判断学生年龄是否都大于18
        boolean flag = list.stream().allMatch(stu -> stu.getAge() > 18);
        System.out.println(flag);
    }
}
