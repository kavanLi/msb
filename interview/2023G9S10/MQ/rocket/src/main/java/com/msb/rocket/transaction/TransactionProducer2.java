/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msb.rocket.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 分布式事务场景（用户A向用户B 转100块钱，用户A在A系统，用户B在B系统）
 * 这里TransactionProducer  是A系统模拟用户A 在A系统进行金额扣减
 */
public class TransactionProducer2 {
    public static void main(String[] args) throws MQClientException, InterruptedException {
        //创建事务监听器(名字加了2)
        TransactionListener transactionListener = new TransactionListenerImpl2();
        TransactionMQProducer producer = new TransactionMQProducer("TransactionProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        //创建线程池
        ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread2");
                return thread;
            }
        });
        //设置生产者回查线程池
        producer.setExecutorService(executorService);
        //生产者设置监听器
        producer.setTransactionListener(transactionListener);
        //启动消息生产者
        producer.start();
        //1、半事务的发送
        try {
            Message msg =
                new Message("TransactionTopic", null, ("用户A向用户B系统转100块钱 ").getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            if(sendResult.getSendStatus()==SendStatus.SEND_OK){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                System.out.println("发送半事务消息成功:"+df.format(new Date()));//半事务消息是否成功
            }else {
                System.out.println("发送半事务消息失败！！！");
                return;
            }
        } catch (MQClientException | UnsupportedEncodingException e) {
            System.out.println("发送半事务消息失败！！！");
            e.printStackTrace();
        }
        Listener(); //启动事务补偿的消费者监听
        //一些长时间等待的业务（比如输入密码，确认等操作）：需要通过事务回查来处理
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(1000);
        }
        producer.shutdown();
    }
    public static void Listener(){
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("balance_consumer");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            consumer.subscribe("ErrTranscation", "*");
            // 注册回调函数(并发消费模式)，处理消息
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                                ConsumeConcurrentlyContext context) {
                    try {
                        for(MessageExt msg : msgs) {
                            String topic = msg.getTopic();
                            String msgBody = new String(msg.getBody(), "utf-8");
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                            //todo 执行本地事务的回退补偿方案.
                            System.out.println("begin");
                            System.out.println("update A ...(A账户加100块) :"+df.format(new Date()));
                            System.out.println("commit "+df.format(new Date()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
                }
            });
            //启动消息者
            consumer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
