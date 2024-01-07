package com.msb.rebalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类说明：设置了再均衡监听器的消费者
 */
public class RebalanceConsumer {

    public static final String GROUP_ID = "rebalance_consumer";
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {

        //先启动两个消费者
        new Thread(new ConsumerWorker(false)).start();
        new Thread(new ConsumerWorker(false)).start();
        Thread.sleep(5000);
        //再启动一个消费，这个消费者 运行几次后就会停止消费
        new Thread(new ConsumerWorker(true)).start();


        //Thread.sleep(5000000);
    }
}
