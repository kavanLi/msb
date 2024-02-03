package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

public class ReactorDemo10 {
    public static void main(String[] args) throws InterruptedException {
//        Flux.just(1,2,3,4,5,6)
//                .any(item -> item % 2 == 0)
//                .subscribe(System.out::println);
//
//        Flux.just(1,2,3,4,5,6)
//                .doOnNext(item -> System.out.println(item))
//                .any(item -> item % 2 == 0)
//                .subscribe(System.out::println);
        // reduce
        // 1,2,3,4,5
        // 0 1 3 6 10
//        Flux.range(1,5)
//                .reduce(0,(item1,item2)->{
//                    System.out.println("item1:" + item1);
//                    System.out.println("item2:" + item2);
//                    return item1 + item2;
//                }).subscribe(System.out::println);

        // scan
//        Flux.range(1,5)
//                .scan(0,(num1,num2) ->{
//                    System.out.println("num1:" + num1);
//                    System.out.println("num2:" + num2);
//                    return num1 + num2;
//                })
//                .subscribe(System.out::println);
//        int arrLength = 5;
//        Flux.just(1,2,3,4,5,6)
//                .index()
//                .scan(new int[arrLength],(arr,entry) ->{
//                    arr[(int) (entry.getT1() % arrLength)] = entry.getT2();
//                    return arr;
//        }).skip(arrLength)// 当窗口数组背灌满之后，开始计算平均值，因此要跳过没有灌满的情况
//                .map(array -> Arrays.stream(array).sum() * 1.0 /arrLength)
//                .subscribe(System.out::println);

        Flux.merge(
                Flux.range(10, 5).delayElements(Duration.ofMillis(100))
                        .doOnSubscribe(subscription -> System.out.println("订阅第1个流")),
                Flux.range(1000, 5).delayElements(Duration.ofMillis(100))
                        .doOnSubscribe(subscription -> System.out.println("订阅第2个流"))
        )
                .subscribe(System.out::println);
        Thread.sleep(10000);


    }
}
