package org.apache.rocketmq.example.details;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 消息消费时的细节
 */
public class ComuserDetails {
    public static void main(String[] args) throws Exception {
        //todo 属性
        //todo consumerGroup：消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("king");
        //todo 指定Namesrv地址信息.
        consumer.setNamesrvAddr("106.55.246.66:9876");
        //todo 消息消费模式（默认集群消费）
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //todo 指定消费开始偏移量（上次消费偏移量、最大偏移量、最小偏移量、启动时间戳）开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        //todo 消费者最小线程数量(默认20)
        consumer.setConsumeThreadMin(20);
        //todo 消费者最大线程数量(默认20)
        consumer.setConsumeThreadMax(20);
        //todo 推模式下任务间隔时间(推模式也是基于不断的轮训拉取的封装)
        consumer.setPullInterval(0);
        //todo 推模式下任务拉取的条数,默认32条(一批批拉)
        consumer.setPullBatchSize(32);
        //todo 消息重试次数,-1代表16次 （超过 次数成为死信消息）
        consumer.setMaxReconsumeTimes(-1);
        //todo 消息消费超时时间(消息可能阻塞正在使用的线程的最大时间：以分钟为单位)
        consumer.setConsumeTimeout(15);




        //todo 获取消费者对主题分配了那些消息队列
        Set<MessageQueue> MessageQueueSet  = consumer.fetchSubscribeMessageQueues("TopicTest");
        Iterator iterator = MessageQueueSet.iterator();
        while(iterator.hasNext()){
            MessageQueue MessageQueue =(MessageQueue)iterator.next();
            System.out.println(MessageQueue.getQueueId());
        }
        //todo 方法-订阅
        //todo 基于主题订阅消息，消息过滤使用表达式
        consumer.subscribe("TopicTest", "*"); //tag  tagA|TagB|TagC
        //todo 基于主题订阅消息，消息过滤使用表达式
        consumer.subscribe("TopicTest",MessageSelector.bySql("a between 0 and 3"));
        //todo 基于主题订阅消息，消息过滤使用表达式
        consumer.subscribe("TopicTest",MessageSelector.byTag("tagA|TagB"));
        //todo 取消消息订阅
        consumer.unsubscribe("TopicTest");

        //todo 注册监听器
        //todo 注册并发事件监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    for(MessageExt msg : msgs) {
                        String topic = msg.getTopic();
                        String msgBody = new String(msg.getBody(), "utf-8");
                        String tags = msg.getTags();
                        System.out.println("收到消息：" + " topic :" + topic + " ,tags : " + tags + " ,msg : " + msgBody);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //没有成功  -- 到重试队列中来
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;

                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                //todo
            }
        });

        //todo 注册顺序消息事件监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            Random random = new Random();
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                for (MessageExt msg : msgs) {
                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
                    System.out.println("consumeThread=" + Thread.currentThread().getName() + "queueId=" + msg.getQueueId() + ", content:" + new String(msg.getBody()));
                }
                try {
                    //模拟业务逻辑处理中...
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
                } catch (Exception e) {
                    e.printStackTrace();
                    //todo 这个点要注意：意思是先等一会，一会儿再处理这批消息，而不是放到重试队列里
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
