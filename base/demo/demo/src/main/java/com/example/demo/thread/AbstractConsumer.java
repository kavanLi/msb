package com.example.demo.thread;

/**
 * Java 实现生产者 : 消费者模型
 * https://mp.weixin.qq.com/s/Hl8Ms3hS3W5j-b0FPHaIsg
 *
 * @author: kavanLi
 * @create: 2019-01-11 15:37
 * To change this template use File | Settings | File and Code Templates.
 */
public abstract class AbstractConsumer implements Consumer, Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
