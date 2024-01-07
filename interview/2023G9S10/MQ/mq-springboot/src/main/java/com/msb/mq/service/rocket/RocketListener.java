package com.msb.mq.service.rocket;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@RocketMQMessageListener(topic = "${rocket.order.topic}",consumerGroup = "${rocket.order.consumer.group.name}",messageModel = MessageModel.CLUSTERING)
public class RocketListener implements RocketMQListener<MessageExt> {
    private static final Logger logger = LoggerFactory.getLogger(RocketListener.class);
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            //1.解析消息内容
            String body = new String(messageExt.getBody(),"UTF-8");
            long lBorn =messageExt.getBornTimestamp();
            long lStore =messageExt.getStoreTimestamp();
            System.out.println("Rocket-Consumer-Receiver : " + body  +",BornTime:"+this.stampToTime(lBorn)+",StoreTime:"+this.stampToTime(lStore)+"");
        } catch (UnsupportedEncodingException e) {
            logger.error("订阅消息：${mq.order.topic} 失败：[" + messageExt.getBody().toString() + "]");
            e.printStackTrace();
        }
    }

    public static String stampToTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将时间戳转换为时间
        Date date = new Date(time);
        //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        String res = simpleDateFormat.format(date);
        return res;
    }
}
