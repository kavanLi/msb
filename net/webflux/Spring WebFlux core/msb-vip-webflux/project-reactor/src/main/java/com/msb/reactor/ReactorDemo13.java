package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

public class ReactorDemo13 {
    public static void main(String[] args) throws InterruptedException {
        //flatMap
//        Random random = new Random();
//        Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
//                .doOnNext(System.out::println)
//                .flatMap(item -> Flux.fromIterable(item)
//                .doOnSubscribe(subscription -> {
//                    System.out.println("已经订阅");
//                })// 我们增加一个延时，订阅后延时一段时间再发送
//                .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
//                ).subscribe(System.out::println);
//
//        Thread.sleep(10*1000);

        // concatMap
//        Random random = new Random();
//        Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
//                .doOnNext(System.out::println)
//                .concatMap(item -> Flux.fromIterable(item)
//                        .doOnSubscribe(subscription -> {
//                            System.out.println("已经订阅");
//                        })// 我们增加一个延时，订阅后延时一段时间再发送
//                        .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
//                ).subscribe(System.out::println);
//
//        Thread.sleep(10*1000);
        // flatMapSequential
        Random random = new Random();
        Flux.just(Arrays.asList(1,2,3),Arrays.asList("a","b","c","d"),Arrays.asList(7,8,9))
                .doOnNext(System.out::println)
                .flatMapSequential(item -> Flux.fromIterable(item)
                        .doOnSubscribe(subscription -> {
                            System.out.println("已经订阅");
                        })// 我们增加一个延时，订阅后延时一段时间再发送
                        .delayElements(Duration.ofMillis(random.nextInt(100) + 100))
                ).subscribe(System.out::println);

        Thread.sleep(10*1000);
    }
}