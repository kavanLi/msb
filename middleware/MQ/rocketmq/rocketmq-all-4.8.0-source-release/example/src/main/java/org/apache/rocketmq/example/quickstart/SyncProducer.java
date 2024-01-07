package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
/**
 * 同步发送  原生的API :SpringBoot   封装-> 原生
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("group_test");

        // 设置NameServer的地址（Broker有多台，  2主（对生产消费）2从（数据备份）的架构）  避免：单点故障
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
       // producer.setDefaultTopicQueueNums(8);


        for (int i = 0; i < 10; i++) { //发送100条消息
            // 创建消息，并指定Topic（消息进行分类： 衣服、电器、手机），Tag（男装、女装、童装   -》消费环节：过滤）和消息体
            //
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);//
            System.out.printf("%s%n", sendResult);
        }
        //如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
