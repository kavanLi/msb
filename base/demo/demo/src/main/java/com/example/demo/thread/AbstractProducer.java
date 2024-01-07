package com.example.demo.thread;

/**
 * @author: kavanLi
 * @create: 2019-01-11 15:38
 * To change this template use File | Settings | File and Code Templates.
 */
public abstract class AbstractProducer implements Producer, Runnable {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
