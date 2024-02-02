package com.mashibing.example02;

/**
 * @author spikeCong
 * @date 2022/9/19
 **/
public class App {

    public static void main(String[] args) {

        //获取连接对象
        RabbitMQClient3 instance = new RabbitMQClient3.Builder().setHost("192.168.52.123").
                setMode(1).setPort(5672).setQueue("test").build();

        instance.sendMessage("test");
    }
}
