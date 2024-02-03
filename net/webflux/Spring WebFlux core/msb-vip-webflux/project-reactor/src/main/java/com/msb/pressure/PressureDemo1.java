package com.msb.pressure;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class PressureDemo1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.range(1,1000)
                .delayElements(Duration.ofMillis(10))
                .onBackpressureError()
                .delayElements(Duration.ofMillis(100))
                .subscribe(
                        System.out::println,
                        ex ->{
                            System.out.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
        System.out.println("main结束");
    }
}
