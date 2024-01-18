package com.msb.hotAndCold;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

public class HotAndCold {
    public static void main(String[] args) throws InterruptedException {
//        Flux<String> coldPublisher = Flux.defer(()->{
//            System.out.println("生成数据");
//            return Flux.just(UUID.randomUUID().toString());
//        });
//        System.out.println("尚未生成数据");
//        coldPublisher.subscribe(e -> System.out.println("onNext:" + e));
//        coldPublisher.subscribe(e -> System.out.println("onNext:" + e));
//        System.out.println("为两次订阅生成两次数据");

//        Flux<Integer> source = Flux.range(0,3)
//                .doOnSubscribe(s -> System.out.println("对冷发布者的新订阅票据：" + s));
//        ConnectableFlux<Integer> conn = source.publish();
//        conn.subscribe(item -> System.out.println("[subscriber 1] onNext:" + item));
//        conn.subscribe(item -> System.out.println("[subscriber 2] onNext:" + item));
//        System.out.println("所有定于这都准备建立连接");
//        conn.connect();

//        Flux<Integer> source = Flux.range(0,5)
//                .doOnSubscribe(s -> System.out.println("冷发布者的新订阅数据"));
//        Flux<Integer> cacheSource = source.cache(Duration.ofMillis(1000));
//        cacheSource.subscribe(item -> System.out.println("[subscribe 1] on Next:" +item));
//        cacheSource.subscribe(item -> System.out.println("[subscribe 2] on Next:" +item));
//        Thread.sleep(1200);
//        cacheSource.subscribe(item -> System.out.println("[subscribe 3] on Next:" +item));


        Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(s -> System.out.println("冷发布者新的订阅票据"));
        Flux<Integer> shareSource = source.share();
        shareSource.subscribe(item -> System.out.println("subscribe 1 onNext:" + item));
        Thread.sleep(400);
        shareSource.subscribe(item -> System.out.println("subscribe 2 onNext:" + item));
        Thread.sleep(10*1000);
    }
}
