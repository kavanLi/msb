package com.msb.rocket.scheduled;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
/**
 * 延时消息-生产者
 */
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("ScheduledProducer");
        // 设置NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 启动Producer实例
        producer.start();
        int totalMessagesToSend = 1;



        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("ScheduledTopic", ("Hello scheduled message " + i).getBytes());
            // 设置延时等级4,这个消息将在30s之后才投递给消费者(详看delayTimeLevel)
            // delayTimeLevel：(1~18个等级)"1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
             // message.setDelayTimeLevel(4);
            //5版本支持了定时消息，设定延时任意时间
             message.setDelayTimeSec(19); //延时的时间秒
            //message.setDelayTimeMs(5*1000);  //延时的时间毫秒
            // 发送消息
            producer.send(message);
        }
        // 关闭生产者
        producer.shutdown();
    }
}
