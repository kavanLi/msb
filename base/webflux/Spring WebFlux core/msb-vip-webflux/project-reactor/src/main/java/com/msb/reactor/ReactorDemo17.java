package com.msb.reactor;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class ReactorDemo17 {
    public static void main(String[] args) throws InterruptedException {
        Flux.just(1,2,3)
                .delayElements(Duration.ofMillis(1000))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new Exception("手动异常")))
                .materialize()
                .doOnEach(item -> System.out.println(item.isOnComplete()))
                .log()
                .dematerialize()
                .subscribe(System.out::println);
        Thread.sleep(10*1000);
    }
}
