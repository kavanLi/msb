package cn.edu.compent;

import cn.edu.model.ShopOrder;
import cn.edu.service.GoodsServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderListener implements MessageListenerConcurrently {

    @Autowired
    private GoodsServiceImpl goodsService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        try{
            //1.解析消息内容
            for (MessageExt message:list) {
                String body = new String(message.getBody(),"UTF-8");
                //TODO 使用GSON反序列化
                Gson gson = new Gson();
                if(1==1){
                    throw new Exception();
                }
                ShopOrder order = (ShopOrder)gson.fromJson(body, ShopOrder.class);
                long goodsId =order.getGoodsId();
                Integer goodsNumber=order.getGoodsNumber();
                goodsService.updateGoods(goodsId,goodsNumber);
                System.out.println("处理消费者数据：成功:"+message.getTransactionId());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            System.out.println("处理消费者数据发生异常"+e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}