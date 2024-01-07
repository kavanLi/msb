package com.msb.mq.controller;


import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

 *类说明： RocketMQ生产者
 */
@RestController
@RequestMapping("/rocket")
public class RocketProducer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通类型测试
     */
    @GetMapping("/send")
    public String send() throws Exception{ //mq的消息发送
        String txtMsg = "hello rocket";
        Message message = new Message("orderTopic",null,null,txtMsg.getBytes());
        SendResult sendResult = rocketMQTemplate.getProducer().send(message);

        rocketMQTemplate.getProducer().sendOneway(message);
        //rocketMQTemplate.getProducer().setRetryTimesWhenSendFailed(5);//重试5次  默认 RocketMq客户端 2次
        if(sendResult.getSendStatus() == SendStatus.SEND_OK){
            return  "MQ发送消息成功！！";
        }else{
            return  "MQ发送消息失败！！！！！！！！";
        }
    }

    @GetMapping("/delay")
    public String sendDelay() throws Exception{ //mq的消息发送
        String txtMsg = "hello rocket";
        Message message = new Message("orderTopic",null,null,txtMsg.getBytes());

        // delayTimeLevel：(1~18个等级)"1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"

        //在 RocketM 4.X  只能玩18个等级（收费版可以，apache开源不行）， 到5版本的是可以---自定义的、
        message.setDelayTimeLevel(3);
        SendResult sendResult = rocketMQTemplate.getProducer().send(message);
        if(sendResult.getSendStatus() == SendStatus.SEND_OK){
            return  "MQ发送消息成功！！";
        }else{
            return  "MQ发送消息失败！！！！！！！！";
        }
    }


}
