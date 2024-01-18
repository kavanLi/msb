package com.msb.reactor;

import reactor.core.publisher.Flux;

import java.util.*;

public class ReactorDemo9 {
    public static void main(String[] args) {
//        Flux.just(1,2,36,4,25,6,7)
//                //CollectionSoredList 默认是升序
//                .collectSortedList(Comparator.reverseOrder())
//                .subscribe(System.out::println);

//        Flux.just(1,2,3,4,5,6)
//                .collectMap(item -> "key:num" + item)
//                .subscribe(System.out::println);
//        Flux.just(1,2,3,4,5,6)
//                .collectMap(
//                        item -> "key:" + item,
//                        item -> "value:" + item
//                ).subscribe(System.out::println);
//
//        Flux.just(1,2,3,4,5,6)
//                .collectMap(
//                        integer -> "key:" + integer,
//                        integer -> "value:" + integer,
//                        ()->{
//                            Map<String,String> map = new HashMap<>();
//                            for(int i =7 ;i <10;i++){
//                                map.put("key:" + i ,"value:" + i);
//                            }
//                            return map;
//
//                        }
//                ).subscribe(System.out::println);


//        Flux.just(1,2,3,4,5)
//                .collectMultimap(
//                        item -> "key:" + item,
//                        item ->{
//                            List<String> values = new ArrayList<>();
//                            for(int i =0 ;i < item ;i ++){
//                                values.add("value:" + i);
//                            }
//                            return values;
//                        }
//                ).subscribe(System.out::println);
//        Flux.just(1,2,3,4,5)
//                .collectMultimap(
//                        item -> "key:" + item,
//                        item ->{
//                            List<String> values = new ArrayList<>();
//                            for(int i =0 ;i < item ;i ++){
//                                values.add("value:" + i);
//                            }
//                            return values;
//                        },// 扩充
//                        () ->{
//                            Map map = new HashMap<String,List>();
//                            List<String> list = new ArrayList<>();
//                            for(int i = 0 ;i <3;i ++){
//                                list.clear();
//                                for(int j = 0 ;j <i;j++){
//                                    list.add("ele:" +j);
//                                }
//                                map.put(i + ":key",list);
//                            }
//                            return map;
//                        }
//                ).subscribe(System.out::println);

//        Flux.just(1,2,3)
//                .repeat(3)// 实际上是打印四次，一次原始的，三次重复的
//                .subscribe(System.out::println);
//        Flux.empty().defaultIfEmpty("hello msb").subscribe(System.out::println);
//            Flux.just(1,2,3)
//                    .repeat(3)
//                    .distinct()
//                    .subscribe(System.out::println);

//        Flux.just(1,1,1,2,2,2,3,3,3,1,1,1,2,2,2)
//                .distinctUntilChanged()
//                .subscribe(System.out::print);
//        System.out.println();
//        System.out.println("===============");
//        Flux.just(1,1,1,2,2,2,3,3,3,1,1,1,2,2,2)
//                .distinct()
//                .subscribe(System.out::print);
    }
}
