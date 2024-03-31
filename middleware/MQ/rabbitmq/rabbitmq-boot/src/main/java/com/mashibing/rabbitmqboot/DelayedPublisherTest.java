package com.mashibing.rabbitmqboot;

import com.mashibing.rabbitmqboot.config.DelayedConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zjw
 * @description
 */
@SpringBootTest
public class DelayedPublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void publish(){
        rabbitTemplate.convertAndSend(DelayedConfig.DELAYED_EXCHANGE, "delayed.abc", "xxxx", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDelay(30000);
                return message;
            }
        });
    }
}
