package org.apache.rocketmq.example.others;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class SyncProducer {
    public static void main(String[] args) throws Exception{
        //实例化消息生产者
        DefaultMQProducer producer = new DefaultMQProducer("group_test");
        //设置nameserver的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
      //  producer.setSendLatencyFaultEnable(true);
        //启动消息生产者
        producer.start();
        //创建消息并发送
        for (int i = 1; i <= 10; i++){
            Message msg = new Message("TopicTest", "TagA", ("这是第" + i + "条消息").getBytes());
            SendResult result = producer.send(msg);
            System.out.println(result);
        }
        producer.shutdown();
    }
}
