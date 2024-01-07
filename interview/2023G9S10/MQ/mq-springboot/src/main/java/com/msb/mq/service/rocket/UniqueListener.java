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
@RocketMQMessageListener(topic = "order2",consumerGroup = "group-2",messageModel = MessageModel.CLUSTERING)
public class UniqueListener implements RocketMQListener<MessageExt> {
    private static final Logger logger = LoggerFactory.getLogger(UniqueListener.class);
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            //去重表--、确保幂等性：可以选用数据库或者redis进行替代
            //1、数据库插入一张表，这张表key不重复,
            //   messageExt.getKeys()
//                        try{
//                            shopGoodsUnique shopGoodsUnique = new shopGoodsUnique();
//                            shopGoodsUnique.setOrderId(orderId);
//                            shopGoodsUniqueMapper.insert(shopGoodsUnique);//如果这个地方消息重复了\异常
//                        }catch (Exception e){//如果异常了呢\
//                            logger.error("重复的修改库存信息：[" + orderId + "]");
//                            //完美的 方案又写一个topic 的数据（重复的），这个数据 可以用来分析
//                            return ;
//                        }
            //2、Redis每次操作 先查询在key-unq某个集合中存不存在，如果存在则直接返回，如果不存在则插入这个集合


            //3.解析消息内容
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
