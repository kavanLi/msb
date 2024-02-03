package com.msb.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class ReactorDemo6 {
    public static void main(String[] args) {

        //1、subscribe
//        Flux.range(100,10)
//                .filter(num -> num % 3 == 0)
//                .map(num -> "hello msb " + num)
//                .doOnNext(System.out::println)
//                .subscribe();
        //2、增加订阅者
//        Flux.range(100,10)
//                .filter( num -> num % 2 == 0)
//                .subscribe(System.out::println);
        // 3、增加对异常的处理
//        Flux.from(subscirber -> {
//            for(int  i =0;i< 5;i++){
//                subscirber.onNext(i);
//            }
//            subscirber.onError(new Exception("测试数据异常"));
//        }).subscribe(
//                num -> System.out.println(num),
//                ex -> System.err.println("异常情况：" +ex)
//        );
        //4、 完成事件的处理
//        Flux.from((Publisher<Integer>) s -> {
//            for(int i = 0 ;i < 10;i++){
//                s.onNext(i);
//            }
//            s.onComplete();
//        }).subscribe(
//                item -> System.out.println("onNext :"+ item),
//                ex -> System.out.println("异常情况：" +ex),
//                () -> System.out.println("处理完毕")
//        );
        // 5、手动控制订阅
//        Flux.range(1,100)
//                .subscribe(
//                        data -> System.out.println("onNext:" + data),
//                        ex -> System.err.println("异常信息：" +ex),
//                        ()-> System.out.println("onComplete"),
//                        subscription -> {
//                            subscription.request(5);
//                            subscription.cancel();
//                        }
//                );
        //6、实现自定义订阅者
        Subscriber<String> subscriber = new Subscriber<String>() {
            volatile Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                System.out.println("initial request for 1 element");
                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
                System.out.println("requesting 1 more element" );
                subscription.request(1);

            }

            @Override
            public void onError(Throwable t) {
                System.err.println("出现异常：" +t);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };
        Flux<String> stream = Flux.just("hello", "everyone", "!");
        stream.subscribe(subscriber);


    }
}
