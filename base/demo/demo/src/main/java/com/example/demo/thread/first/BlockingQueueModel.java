package com.example.demo.thread.first;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.demo.thread.AbstractConsumer;
import com.example.demo.thread.AbstractProducer;
import com.example.demo.thread.Consumer;
import com.example.demo.thread.Model;
import com.example.demo.thread.Producer;
import com.example.demo.thread.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: kavanLi
 * @create: 2019-01-11 16:02
 * To change this template use File | Settings | File and Code Templates.
 */
public class BlockingQueueModel implements Model {

    private final BlockingQueue<Task> queue;

    private final AtomicInteger increaseTaskNo = new AtomicInteger(0);

    private static final Logger logger = LoggerFactory.getLogger(BlockingQueueModel.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BlockingQueueModel(int cap) {
        // LinkedBlockingQueue 的队列是 lazy-init 的，但 ArrayBlockingQueue 在创建时就已经 init
        this.queue = new LinkedBlockingQueue <>(cap);
    }

    @Override
    public Runnable newRunnableConsumer() {
        return new ConsumerImpl();
    }

    @Override
    public Runnable newRunnableProducer() {
        return new ProducerImpl();
    }

    private class ConsumerImpl extends AbstractConsumer implements Consumer, Runnable {

        @Override
        public void consume() throws InterruptedException {
            Task take = queue.take();
            // 固定时间范围的消费，模拟相对稳定的服务器处理过程
            Thread.sleep(500 + (long)(Math.random() * 500));
            logger.info("Consume Time - {}, consume: {}.", dateTimeFormatter.format(LocalDateTime.now()), take.no);
        }
    }

    private class ProducerImpl extends AbstractProducer implements Producer, Runnable {

        @Override
        public void produce() throws InterruptedException {
            // 不定期生产，模拟随机的用户请求
            Thread.sleep((long)(Math.random() * 1000));
            Task task = new Task(increaseTaskNo.getAndIncrement());
            queue.put(task);
            logger.info("Consume Time - {}, produce: {}.", dateTimeFormatter.format(LocalDateTime.now()), task.no);
            // kill thread
            boolean alive = Thread.currentThread().isAlive();
            Thread.State state = Thread.currentThread().getState();
            boolean alive3dd1 = Thread.currentThread().isInterrupted();
            Thread.currentThread().interrupt();
            Thread.currentThread().join();
            boolean alive1 = Thread.currentThread().isAlive();
            boolean alive31 = Thread.currentThread().isInterrupted();
            Thread.State state2 = Thread.currentThread().getState();
            Thread.currentThread().run();

        }
    }

    public static void main(String[] args) {
        Model model = new BlockingQueueModel(3);
        for (int i = 0; i < 2; i++) {
            new Thread(model.newRunnableConsumer()).start();
        }
        //for (int i = 0; i < 5; i++) {
        //    new Thread(model.newRunnableProducer()).start();
        //}
        Thread thread = new Thread(model.newRunnableProducer());
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
