package com.mashibing.rabbitmqboot;

import com.mashibing.rabbitmqboot.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * @author zjw
 * @description
 * @date 2022/2/8 21:05
 */
@SpringBootTest
public class PublisherTest {

    @Autowired
    public RabbitTemplate rabbitTemplate;

    @Test
    public void publish(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,"big.black.dog","message");
        System.out.println("消息发送成功");
    }


    @Test
    public void publishWithProps(){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "messageWithProps", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId("456");
                return message;
            }
        });
        System.out.println("消息发送成功");
    }


    @Test
    public void publishWithConfirms() throws IOException {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    System.out.println("消息已经送达到交换机！！");
                }else{
                    System.out.println("消息没有送达到Exchange，需要做一些补偿操作！！retry！！！");
                }
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,"big.black.dog","message");
        System.out.println("消息发送成功");

        System.in.read();
    }

    @Test
    public void publishWithReturn() throws IOException {
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                String msg = new String(returned.getMessage().getBody());
                System.out.println("消息：" + msg + "路由队列失败！！做补救操作！！");
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,"big.black.dog","message");
        System.out.println("消息发送成功");

        System.in.read();
    }

    @Test
    public void publishWithBasicProperties() throws IOException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "big.black.dog", "message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                return message;
            }
        });
        System.out.println("消息发送成功");
    }
}
