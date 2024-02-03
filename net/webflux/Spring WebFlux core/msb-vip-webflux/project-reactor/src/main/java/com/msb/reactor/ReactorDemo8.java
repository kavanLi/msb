package com.msb.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ReactorDemo8 {
    public static void main(String[] args) throws InterruptedException {
        Flux.interval(Duration.ofMillis(500))
                .map(item -> "msb " + item)
                .doOnNext(System.out::println)
                .skipUntilOther(Mono.just("start").delayElement(Duration.ofSeconds(3)))
                .takeUntilOther(Mono.just("end").delayElement(Duration.ofSeconds(6)))
                .subscribe(
                        item -> System.out.println("onNext: " + item),
                        ex -> System.err.println("onError: " + ex),
                        () -> System.out.println("onCompleted")
                );
        Thread.sleep(10*1000);
    }
}

