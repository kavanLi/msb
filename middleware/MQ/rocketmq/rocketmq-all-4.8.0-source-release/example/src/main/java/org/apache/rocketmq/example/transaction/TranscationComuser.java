package org.apache.rocketmq.example.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 事务消息-消费者 B
 */
public class TranscationComuser {
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
                        //todo 执行本地事务 update B...（幂等性）
                        System.out.println("update B ... where transactionId:"+msg.getTransactionId());
                        //todo 本地事务成功
                        System.out.println("commit:"+msg.getTransactionId());
                        System.out.println("执行本地事务成功，确认消息");
                    }
                } catch (Exception e) {
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
