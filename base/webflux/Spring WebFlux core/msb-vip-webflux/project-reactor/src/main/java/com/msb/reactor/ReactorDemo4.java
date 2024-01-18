package com.msb.reactor;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public class ReactorDemo4 {
    public static void main(String[] args) {
        Flux.from((Publisher<String>) s -> {
            for(int i = 0 ;i < 10 ;i++){
                s.onNext("hello " + i);
            }
            s.onComplete();
        }).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("处理结束")
        );
    }
}
