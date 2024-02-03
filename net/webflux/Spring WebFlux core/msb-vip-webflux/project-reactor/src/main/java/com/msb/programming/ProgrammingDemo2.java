package com.msb.programming;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;

public class ProgrammingDemo2 {
    public static void main(String[] args) throws InterruptedException {
        Flux.generate(
                // 通过Callable提供初始状态实例
                () -> Tuples.of(0L,1L), // 负责斐波拉契数列
                // 函数第一个参数数据、函数第二个参数类型 、返回值
               (state, sink) -> {
                   System.out.println("生成的数字：" + state.getT2());
                   sink.next(state.getT1());
                   long nextValue  = state.getT1() + state.getT2();
                    return Tuples.of(state.getT2(),nextValue);
                }).delayElements(Duration.ofMillis(500))
                .take(10)
                .subscribe(System.out::println);
        Thread.sleep(5000);
    }
}
