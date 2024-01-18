package com.msb.error;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ErrorDemo1 {
    private static Random random = new Random();
    private static Flux<String> recommendedBooks(String userId){
        return Flux.defer(()->{
            if(random.nextInt(10) < 7){
                return Flux.<String>error(new RuntimeException("err"))
                        // 整体向后推移时间
                        .delaySequence(Duration.ofMillis(100));
            }else{
                return Flux.just("西游记","红楼梦")
                        .delayElements(Duration.ofMillis(10));
            }
        }).doOnSubscribe(
                item -> System.out.println("请求：" + userId)
        );
    }
    private static CountDownLatch latch = new CountDownLatch(1);
    public static void main(String[] args) throws InterruptedException {

        Flux.just("user-1000")
                .flatMap(user -> recommendedBooks(user))
                .onErrorMap(throwable -> {
                    if(throwable.getMessage().equals("err")){
                        return new Exception("业务异常");
                    }
                    return new Exception("未知异常");
                })
                .subscribe(
                        System.out::println,
                        ex -> {
                            System.err.println(ex);
                            latch.countDown();
                        },
                        () ->{
                            System.out.println("处理完毕");
                            latch.countDown();
                        }
                );
        latch.await();
    }
}
