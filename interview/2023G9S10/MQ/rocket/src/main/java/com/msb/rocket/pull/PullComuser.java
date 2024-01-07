package com.msb.rocket.pull;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.Set;

//pull拉取模式消费
public class PullComuser {
    public static void main(String[] args) throws Exception {
        // 实例化消费者--拉模式--订阅模式
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("TopicTest_pull");
        // 指定Namesrv地址信息.
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.start();
        //获取指定主题的所有消息队列
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");
        for (MessageQueue messageQueue : messageQueues) {//遍历消息队列
            long offset =0;    //这里需要程序自主存储之前的每个队列的消费偏移量
            for (;;) {
                 // offset  表示本次拉取的起始位置， 100  表示每次拉取的消息数量。
                PullResult pullResult = consumer.pull(messageQueue, "*", offset, 1);
                if (pullResult.getPullStatus() == PullStatus.FOUND) {
                    for (MessageExt messageExt : pullResult.getMsgFoundList()) {
                        System.out.println(new String(messageExt.getBody()));
                    }
                    offset =pullResult.getNextBeginOffset();//下一条
                    //consumer.updateConsumeOffset(messageQueue, offset);//这个提交方法不会提交到broker
                }else if(pullResult.getPullStatus() == PullStatus.NO_NEW_MSG){
                    System.out.println("no new msg");
                    break;
                }
            }
        }

        consumer.shutdown();
    }
}
