package com.msb.mq.service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**

 *类说明：消费死信消息（限时订单的超时处理）
 */
@Component
@RabbitListener(queues = "queue_dlx")
public class ConsumerDlx {
    @RabbitHandler
    public void process(String msg) {
        System.out.println("ConsumerDLx-Receiver : " + msg);
    }
}
