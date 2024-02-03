package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class ReactorDemo15 {
    public static void main(String[] args) throws InterruptedException {
//        Flux.just(1,2,3).toIterable();
//        Stream<Integer> stream = Flux.just(1,2,3,4).toStream();
//        // 1、验证toIterable为阻塞
//        Iterable<Integer> integers = Flux.just(1, 2, 3, 4)
//                .delayElements(Duration.ofSeconds(1))
//                .toIterable();
//        System.out.println("===============");
//        for(Integer num :integers){
//            System.out.println(num);
//        }
//        System.out.println("===============");
//        //  2、我么可以做一下改进
//        Flux.just(1,2,3)
//                .delayElements(Duration.ofSeconds(1))
//                .subscribe(System.out::println);
//        System.out.println("==========");
//        System.out.println("==========");
//        Thread.sleep(10*1000);
//
//        // 3、toStream进行阻塞
//        Stream<Integer> integerStream = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1))
//                .toStream();
//        System.out.println("================");
//        integerStream.forEach(System.out::println);
//        System.out.println("================");
//        // 4 、BlockFirst 只拿第一个，其他不处理
//        Integer integer = Flux.just(1, 2, 3)
//                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(item -> System.out.println("onNext:" + item))
//                .blockFirst();
//        System.out.println("==========");
//        System.out.println(integer);
//        System.out.println("==========");
//        Thread.sleep(10*100);
//
//        //  blocklast 直到流的最后一个元素
//        Integer integer2 = Flux.just(1, 2, 3)
//                .delayElements(Duration.ofSeconds(1))
//                .doOnNext(item -> System.out.println("onNext:" + item))
//                .blockLast();
//        System.out.println("==========");
//        System.out.println(integer2);
//        System.out.println("==========");
//
//        Flux<Integer> integerFlux = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1));
//        integerFlux.subscribe(item -> System.out.println("第一个订阅" + item));
//        integerFlux.subscribe(item -> System.out.println("第二个订阅" + item));
//        integerFlux.subscribe(item -> System.out.println("第三个订阅" + item));
//        Integer integer2 = integerFlux.blockFirst();
//        System.out.println("阻塞最后一个元素：" + integer2);
//        System.out.println("=================");
//        Thread.sleep(10*1000);
        Flux.just(1,2,3)
                .concatWith(Flux.error(new RuntimeException("手动异常")))
                .doOnEach(item -> System.out.println(item))
                .subscribe(
                        item -> System.out.println("onNext:" + item),
                        ex -> System.err.println("onError:" + ex),
                        () -> System.out.println("处理结束")
                );
    }
}
