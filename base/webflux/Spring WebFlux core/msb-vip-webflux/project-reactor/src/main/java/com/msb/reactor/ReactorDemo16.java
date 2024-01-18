package com.msb.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ReactorDemo16 {
    public static void main(String[] args) throws InterruptedException {
//        Flux.just(1,2,3)
//                .concatWith(Flux.error(new RuntimeException("手动异常")))
//              //  .doOnEach(item -> System.out.println(item))
//                .subscribe(
//                        item -> System.out.println("onNext:" + item),
//                        ex -> System.err.println("onError:" + ex),
//                        () -> System.out.println("处理完毕")
//                );
        Flux.just(1, 2, 3).delayElements(Duration.ofMillis(1000))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new Exception("手动异常")))
                .materialize()
                .doOnEach(item -> System.out.println(item.isOnComplete()))
                .log()
                .subscribe(System.out::println);
        Thread.sleep(10000);
    }
}
