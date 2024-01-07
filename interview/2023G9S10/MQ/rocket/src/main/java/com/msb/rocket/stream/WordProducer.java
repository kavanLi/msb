package com.msb.rocket.stream;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Random;

/**
 * 同步发送：比较可靠的场景，消息通知，短信通知
 */
public class WordProducer {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group_test");
        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 启动Producer实例
        producer.start();
        String[] words = {"To be, or not to be,--that is the question:--",
                "Whether 'tis nobler in the mind to suffer",
                "The slings and arrows of outrageous fortune",
                "Or to take arms against a sea of troubles,",
                "And by opposing end them?--To die,--to sleep,--",
                "No more; and by a sleep to say we end",
                "The heartache, and the thousand natural shocks",
                "That flesh is heir to,--'tis a consummation"};
        //发送10条消息
        for (int i = 0; i < words.length; i++) {
            StringBuilder sb = new StringBuilder("");
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("sourceTopic" /* Topic */,
                    null/* Tag */,
                    words[i].toString().getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        //如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
