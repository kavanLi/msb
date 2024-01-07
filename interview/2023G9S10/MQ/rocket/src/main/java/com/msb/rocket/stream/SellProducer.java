package com.msb.rocket.stream;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Random;

/**
 * 同步发送：比较可靠的场景，消息通知，短信通知
 */
public class SellProducer {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group_test");
        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 启动Producer实例
        producer.start();
        String[] words = {"iphone","huawei","xiaomi","oppo","vivo"};
        Random r = new Random();
        //发送100条消息
        for (int i = 0; i < 100; i++) {
            StringBuilder sb = new StringBuilder("");
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("sell" /* Topic */,
                    null/* Tag */,
                    words[r.nextInt(words.length)].toString().getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);  //这里会阻塞  比如发一个文件（2m）
            System.out.printf("%s%n", sendResult);
        }
        //如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
