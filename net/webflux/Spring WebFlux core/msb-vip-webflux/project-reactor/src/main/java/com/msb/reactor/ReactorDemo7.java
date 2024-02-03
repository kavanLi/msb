package com.msb.reactor;

import reactor.core.publisher.Flux;

public class ReactorDemo7 {
    public static void main(String[] args) {
        // map映射
//        Flux.range(1,10)
//                .map(item -> "hello msb" + item)
//                .subscribe(System.out::println);

        // index索引
//        Flux.range(0,10)
//                .map(item -> "hello msb " + item)
//                .index()
//                .subscribe(item ->{
//                    // 二元组第一个元素 ，编号 0开始
//                    Long t1 = item.getT1();
//                    // 二元组第二个元素，也就是具体值
//                    String t2 = item.getT2();
//                    System.out.println(t1 + ":" + t2);
//
//                },
//                        System.err::println,
//                        () -> System.out.println("流已经处理完毕"));


        Flux.range(0,10)
                .map(item -> "hello msb " + item)
                .timestamp()
                .subscribe(item ->{
                            // 二元组第一个元素 ，编号 0开始
                            Long t1 = item.getT1();
                            // 二元组第二个元素，也就是具体值
                            String t2 = item.getT2();
                            System.out.println(t1 + ":" + t2);

                        },
                        System.err::println,
                        () -> System.out.println("流已经处理完毕"));
    }
}
