package cn.edu.service.mq;

import cn.edu.db.DBService;
import cn.edu.model.ShopOrder;
import cn.edu.service.GoodsServiceImpl;
import com.google.gson.Gson;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
@RocketMQMessageListener(topic = "ticket",consumerGroup = "ticket-goods",messageModel = MessageModel.CLUSTERING)
public class TicketListener implements RocketMQListener<MessageExt> {
    private static final Logger logger = LoggerFactory.getLogger(TicketListener.class);

    @Autowired
    private DBService dbService;


    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            //1.解析消息内容
            String body = new String(messageExt.getBody(),"UTF-8");
            //模拟出票……
            dbService.useDb("select ticket ");
            System.out.println("短信或其他的形式通知用户!");
        } catch (Exception e) {
            logger.error("订阅消息：${mq.order.topic} 失败：[" + messageExt.getBody().toString() + "]");
            e.printStackTrace();
        }

    }
}
