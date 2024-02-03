package com.msb.reactor;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ReactorDemo1 {
    public static void main(String[] args) {
        //1、响应式流参数直接列举出来
//        Flux<String> just = Flux.just("hello","everybody");
//        just.subscribe(System.out::println);
        // 2、从数组中创建流
//        Flux<String> stringFlux = Flux.fromArray(new String[]{"hello","lihua","zhangli"});
//        stringFlux.subscribe(System.out::println);
        // 3、实现Iterable接口的集合
//        Flux<Integer> integerFlux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6,7));
//        integerFlux.subscribe(System.out::println);
        // 4、第一个参数就是起点 第二个参数：标识序列中元素个数
        Flux<Integer> range = Flux.range(1000,5);
        range.subscribe(System.out::println);

    }
}
