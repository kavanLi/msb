package com.msb.reactor;

import reactor.core.publisher.Mono;

import java.util.Optional;

public class ReactorDemo2 {
    public static void main(String[] args) {
        //1、指定元素（只能是一个元素）
//        Mono<String> just = Mono.just("hello");
//        just.subscribe(System.out::println);
        // 2、空元素
        Mono<Object> objectMono = Mono.justOrEmpty(null);
        objectMono.subscribe(System.out::println);
        Mono<Object> objectMono1 = Mono.justOrEmpty(Optional.empty());
        objectMono1.subscribe(System.out::println);
    }
}
