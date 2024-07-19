package com.mashibing.rabbitmqboot;

import com.mashibing.rabbitmqboot.config.DeadLetterConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zjw
 * @description
 * @date 2022/2/10 15:17
 */
//@Component
public class DeadListener {

    @RabbitListener(queues = DeadLetterConfig.NORMAL_QUEUE)
    public void consume(String msg, Channel channel, Message message) throws IOException {
        System.out.println("接收到normal队列的消息：" + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
    }

}
