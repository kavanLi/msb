package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class ReactorDemo12 {
    public static void main(String[] args) {
//        Flux.range(1,100)
//                .buffer(10)
//                .subscribe(System.out::println);
//        Flux.range(101,20)
//                .windowUntil(ReactorDemo12::isPrime,true)
//                .subscribe(
//                        window ->
//                            window.collectList()
//                                    .subscribe(
//                                            item -> System.out.println("window:" + item)
//                                    )
//                );

        Flux.range(1,7)
                .groupBy(item -> item % 2 == 0 ? "偶数" : "奇数")
                .subscribe(groupFlux ->groupFlux.scan(new ArrayList<>(),
                        (list,element) ->{
                             list.add(element);
                    return list;

                        }).filter(list -> !list.isEmpty()).
                        subscribe(item -> System.out.println(groupFlux.key() + "======" +item))
                );

    }

//    private static boolean isPrime(Integer integer){
//        double sqrt = Math.sqrt(integer);
//        if(integer <  2){
//            return false;
//        }
//        if(integer == 2 || integer == 3){
//            return true;
//        }
//        if(integer % 2 == 0){
//            return false;
//        }
//        for(int i = 3;i <= sqrt;i++){
//            if(integer % i == 0){
//                return false;
//            }
//        }
//        return true;
//    }
}
