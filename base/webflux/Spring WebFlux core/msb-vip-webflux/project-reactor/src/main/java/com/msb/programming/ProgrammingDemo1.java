package com.msb.programming;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class ProgrammingDemo1 {
    public static void main(String[] args) throws InterruptedException {
        // push
//        Flux<Integer> push = Flux.push(fluxSink -> {
//            // 从数据库中获取数据
//            // FluxSink 追加到响应式流中，这样将命令处理方式，转化为响应式处理方式
//            IntStream.range(1, 10)
//                    .forEach(item -> fluxSink.next(item));
//        });
//        push.subscribe(System.out::println);
//        Thread.sleep(5*1000);

        MyEventProcessor myEventProcessor = new MyEventProcessor();
        Flux<String> bridage = Flux.create(sink -> {
            myEventProcessor.register(new MyEventListener<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    for (String s : chunk) {
                        sink.next(s);
                    }
                }

                @Override
                public void processComplete() {
                    sink.complete();
                }
            });
        });
        bridage.subscribe(
                System.out::println,
                ex -> System.err.println(ex),
                () ->System.out.println("处理完毕")
        );
        myEventProcessor.process();
        Thread.sleep(5*1000);
    }
    static class MyEventProcessor{
        private MyEventListener listener;
        private Random random = new Random();
        void register(MyEventListener listener){
            this.listener = listener;
        }

        public void process(){
            while(random.nextInt(10) % 3 != 0){
                List<String> dataChunk = new ArrayList<>();
                for(int i = 0 ;i < 10;i++){
                    dataChunk.add("data - " + i );
                }
                listener.onDataChunk(dataChunk);
            }
            listener.processComplete();;
        }
    }
    interface  MyEventListener<T>{
        void onDataChunk(List<T> chunk);

        void processComplete();
    }
}

