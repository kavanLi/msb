package com.msb.rocket.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事务消息-消费者 B 所在B系统  要对B进行+100块钱的操作
 */
public class TranscationComuser {
    static ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();
    static AtomicInteger errcount = new AtomicInteger(0);
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TranscationComsuer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("TransactionTopic", "*");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    //todo  开启事务
                    for(MessageExt msg : msgs) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.println("begin");
                        System.out.println("B服务器本地事务  ,TransactionId：" + msg.getTransactionId());
                        System.out.println("update B ...(B账户加100块) :"+df.format(new Date()));
                        System.out.println("commit "+df.format(new Date()));
                    }
                } catch (Exception e) {
                    errcount.getAndIncrement();
                    if(errcount.get()>10){
                        //这里就需要走消息补偿策略(这里是例举了一种)
                        try{
                            DefaultMQProducer producer = new DefaultMQProducer("group_test");
                            producer.setNamesrvAddr("127.0.0.1:9876");
                            producer.start();
                            Message msg = new Message("ErrTranscation" /* Topic */,
                                    "" /* Tag */,
                                    msgs.get(1).getBody() /* Message body */
                            );
                            // 发送消息到一个Broker
                            SendResult sendResult = producer.send(msg);
                        }catch (Exception e2){
                            e2.printStackTrace();
                        }
                        return null;
                    }
                    e.printStackTrace();
                    System.out.println("执行本地事务失败，重试消费，尽量确保B处理成功");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
