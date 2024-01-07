package org.apache.rocketmq.example.cluster;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * 集群--消息发送
 */
public class ProducerCluster {
    public static void main(String[] args) throws Exception{
        //todo producerGroup：生产者所属组(针对 事务消息 高可用)
        DefaultMQProducer producer = new DefaultMQProducer("produce_details");
        //todo 如果是在双主双从这种架构创建，每一个主和从节点上都会有4个队列。
        producer.setDefaultTopicQueueNums(4);
        //todo 设置NameServer设置成2台的意义在于NameServer的高可用 （没有特别的意义）
        producer.setNamesrvAddr("106.55.246.66:9876;94.191.83.120:9876;");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicCluster", "TagA", "OrderID888",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //todo 单向发送
            //todo 1.1发送单向消息
            producer.sendOneway(msg);
        }
        Thread.sleep(10000);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
