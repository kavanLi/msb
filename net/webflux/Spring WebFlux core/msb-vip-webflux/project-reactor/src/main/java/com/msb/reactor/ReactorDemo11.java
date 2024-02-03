package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactorDemo11 {
    public static void main(String[] args) throws InterruptedException {
        // concat
//        Flux.concat(
//                Flux.range(10,5).delayElements(Duration.ofMillis(100))
//                .doOnSubscribe(subscription -> System.out.println("订阅第一个流")),
//                Flux.range(100,5).delayElements(Duration.ofMillis(100))
//                .doOnSubscribe(subscription -> System.out.println("订阅第二流"))
//        ).subscribe(System.out::println);
//        Thread.sleep(10*1000);

        // merger
//        Flux.merge(
//                Flux.range(10,5).delayElements(Duration.ofMillis(100))
//                        .doOnSubscribe(subscription -> System.out.println("订阅第一个流")),
//                Flux.range(100,5).delayElements(Duration.ofMillis(100))
//                        .doOnSubscribe(subscription -> System.out.println("订阅第二流"))
//        ).subscribe(System.out::println);
//        Thread.sleep(10*1000);
        // zip
        //这里最后形成的是一个二元组
//        // 这里面依照最慢的那个进行打印，如果我们两个流的打印个数不同，那就以最少的为准
//        Flux.zip(
//                Flux.range(1,10)
//                .delayElements(Duration.ofMillis(10)),
//                Flux.range(100,10)
//                .delayElements(Duration.ofMillis(10))
//                ).subscribe(System.out::println);
//                Thread.sleep(10*1000);

        // combineLatest
        Flux.combineLatest(
                Flux.range(1,10)
                .delayElements(Duration.ofMillis(1000)),
                Flux.range(100,10)
                .delayElements(Duration.ofMillis(2000)),
                ((integer1, integer2) -> integer1 + "==" +integer2)
        ).subscribe(System.out::println);
        Thread.sleep(10*1000);
    }
}
