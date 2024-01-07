package cn.edu.compent;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderListenerDld implements MessageListenerConcurrently {


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        System.out.println("死信队列：消费者线程监听到消息。");
        try{
            for (MessageExt message:list) {
                System.out.println("死信队列的消息发邮件，要采取补偿策略");
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            System.out.println("死信队列：处理消费者数据发生异常"+e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}