package com.example.demo.thread;

/**
 * @author: kavanLi
 * @create: 2019-01-11 15:44
 * To change this template use File | Settings | File and Code Templates.
 */
public interface Model {
    Runnable newRunnableConsumer();
    Runnable newRunnableProducer();
}
