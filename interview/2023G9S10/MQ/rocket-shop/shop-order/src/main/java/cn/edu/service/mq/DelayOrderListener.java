package cn.edu.service.mq;

import cn.edu.model.ShopOrder;
import cn.edu.service.OrderServiceImpl;
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
@RocketMQMessageListener(topic = "${mq.delay.topic}",consumerGroup = "${mq.delay.consumer.group.name}",messageModel = MessageModel.CLUSTERING)
public class DelayOrderListener implements RocketMQListener<MessageExt> {
    private static final Logger logger = LoggerFactory.getLogger(DelayOrderListener.class);

    @Autowired
    private OrderServiceImpl orderService;
    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            //1.解析消息内容
            String body = new String(messageExt.getBody(),"UTF-8");
            //TODO 使用GSON反序列化
            Gson gson = new Gson();
            ShopOrder order = (ShopOrder)gson.fromJson(body, ShopOrder.class);
            orderService.dealDealyOrder(order);
        } catch (Exception e) {
            logger.error("订阅消息：${mq.order.topic} 失败：[" + messageExt.getBody().toString() + "]");
            e.printStackTrace();
        }
    }
}
