package com.msb.reactor;

import reactor.core.publisher.Mono;

public class ReactorDemo5 {
    static boolean isValidValue(String value){
        System.out.println("调用了 isValidValue的方法");
        return  true;
    }
    static String getData(String value){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "eche:" + value;
    }
    static Mono<String> requestData(String value){
        return isValidValue(value) ? Mono.fromCallable(() -> getData(value)):
                Mono.error(new RuntimeException("isvalid value"));
    }
    static Mono<String> requestDeferData(String value){
        return Mono.defer(()->isValidValue(value) ? Mono.fromCallable(()->getData(value)):
                Mono.error(new RuntimeException()));
    }

    public static void main(String[] args) {
       // requestData("zhangsan");
        requestDeferData("zhangsan").subscribe();
    }
}
