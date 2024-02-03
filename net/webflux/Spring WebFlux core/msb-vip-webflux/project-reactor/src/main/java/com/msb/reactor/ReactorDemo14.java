package com.msb.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

public class ReactorDemo14 {
    public static void main(String[] args) throws InterruptedException {
//       // sample
//        // 每个100ms从流中获取对应的元素
//        Flux.range(1,100)
//                .delayElements(Duration.ofMillis(10))
//                .sample(Duration.ofMillis(200))
//                .subscribe(System.out::println);
//        Thread.sleep(10*1000);
//
//        Random random = new Random();
//        Flux.range(0,20)
//                .delayElements(Duration.ofMillis(100))
//                .sampleTimeout( item -> Mono.delay(Duration.ofMillis(random.nextInt(100) + 50)),20)
//                .subscribe(System.out::println);
//        Thread.sleep(10 * 1000);

    }
}
