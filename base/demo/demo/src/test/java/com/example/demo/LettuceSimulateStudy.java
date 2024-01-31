package com.example.demo;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟Lettuce中支持Reactive的方式
 *
 * @author yizhenqiang
 * @date 2023/8/15 23:53
 */
public class LettuceSimulateStudy {

    // 模拟redis应答的延迟队列
    private static final DelayQueue<DelayedElement> redisResponseQueue = new DelayQueue<>();

    private static final ExecutorService acceptExecutorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        // 先启动监听并模拟Redis 应答的流程
        acceptExecutorService.execute(() -> {
            while (true) {
                try {
                    DelayedElement delayedElement = redisResponseQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (null == delayedElement) {
                        continue;
                    }

                    Schedulers.parallel().schedule(delayedElement::mockResponse);

                } catch (InterruptedException e) {
                }
            }
        });

        // 这里模拟并发进行redis get操作（虽然是for循环串行，但实际处理时是事件驱动多线程的）
        int times = 1000;
        while (times-- > 0) {
            redisGet("abc").subscribe(value -> System.out.println(Thread.currentThread().getName() + " 收到应答 " + value));
        }
    }

    /**
     * 模拟redis的get
     *
     * @param key
     * @return
     */
    static Mono<String> redisGet(String key) {
        return Mono.from(new RedisPublisher(new RedisCommand(key, "get")))
                .doOnSubscribe(subscription -> {
                    System.out.println(Thread.currentThread().getName() + " 开始处理请求");
                })
                .subscribeOn(Schedulers.parallel());
    }

    private static class RedisPublisher implements Publisher<String> {
        private RedisCommand redisCommand;

        public RedisPublisher(RedisCommand redisCommand) {
            this.redisCommand = redisCommand;
        }

        @Override
        public void subscribe(Subscriber<? super String> subscriber) {
            redisCommand.setSubscriber(subscriber);
            subscriber.onSubscribe(new RedisSubscription(this));
        }

        public void sendRedisReq() {
            redisCommand.sendRedisReq();
        }
    }

    private static class RedisSubscription implements Subscription {
        private final RedisPublisher redisPublisher;

        public RedisSubscription(RedisPublisher redisPublisher) {
            this.redisPublisher = redisPublisher;
        }

        @Override
        public void request(long l) {
            // 向Redis发送操作请求
            redisPublisher.sendRedisReq();
        }

        @Override
        public void cancel() {
        }

    }

    private static class RedisCommand {
        private String key;
        private String oper;
        private Subscriber<? super String> subscriber;

        public RedisCommand(String key, String oper) {
            this.key = key;
            this.oper = oper;
        }

        public void sendRedisReq() {
            // 这里模拟发送Redis请求的过程，这里其实是直接给延迟队列添加个元素，延迟时间代表redis的处理耗时
            redisResponseQueue.add(new DelayedElement(subscriber, new Random().nextInt(100)));
        }

        public void setSubscriber(Subscriber<? super String> subscriber) {
            this.subscriber = subscriber;
        }
    }

    private static class DelayedElement implements Delayed {
        private long delayTime;
        private long expireTime;
        private Subscriber<? super String> subscriber;

        public DelayedElement(Subscriber<? super String> subscriber, long delayTime) {
            this.subscriber = subscriber;
            this.delayTime = delayTime;
            this.expireTime = System.currentTimeMillis() + delayTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expireTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /**
         * 模拟Redis异步应答
         * 这里假定所有redis命令的操作结果都是一个随机字符串（实际过程中应该是Netty的ChannelInboundHandler#channelRead来触发该方法调用）
         */
        void mockResponse() {
            subscriber.onNext(UUID.randomUUID().toString());
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.expireTime, ((DelayedElement) o).expireTime);
        }
    }
}