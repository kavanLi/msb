package com.msb.mq.service;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**

 *类说明：  消费queue1
 */
@Component
public class Receiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String msg = new String(message.getBody());
            try {
                System.out.println("Receiver>>>>>>消息已消费");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                //Reject在拒绝消息时,一次只能拒绝一条消息。
                //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
                //Nack则可以一次性拒绝多个消息
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
                System.out.println("Receiver>>>>>>拒绝消息，要求Mq重新派发");
                throw e;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
