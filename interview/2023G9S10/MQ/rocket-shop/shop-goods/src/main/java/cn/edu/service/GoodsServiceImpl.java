package cn.edu.service;

import cn.edu.dao.ShopGoodsMapper;
import cn.edu.dao.shopGoodsUniqueMapper;
import cn.edu.model.ShopGoods;
import cn.edu.model.ShopOrder;
import cn.edu.model.shopGoodsUnique;
import com.google.gson.Gson;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *@author King老师
 *
 *类说明：订单相关的服务
 */
@Service
@Transactional
public class GoodsServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

	@Autowired
	private ShopGoodsMapper shopGoodsMapper;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Autowired
    private shopGoodsUniqueMapper shopGoodsUniqueMapper;
    //2、幂等性问题
    public synchronized   int updateGoods(long orderId, long goodsId, int goodsNumber){
        //去重表
        try{
            shopGoodsUnique shopGoodsUnique = new shopGoodsUnique();
            shopGoodsUnique.setOrderId(orderId);
            shopGoodsUniqueMapper.insert(shopGoodsUnique);//如果这个地方消息重复了\异常
        }catch (Exception e){//如果异常了呢\
            logger.error("重复的修改库存信息：[" + orderId + "]");
            //完美的 又写一个topic 的数据（重复的），这个数据 可以用来分析
            return 1;
        }

        //对共享一个东东 进行多线程操作呢？
        ShopGoods shopGoods =shopGoodsMapper.selectByPrimaryKey(goodsId);
        Integer goodnumber = shopGoods.getGoodsNumber()-goodsNumber;
        shopGoods.setGoodsNumber(goodnumber);
        if(shopGoodsMapper.updateByPrimaryKey(shopGoods)>=0){
            //logger.info("修改库存成功：[" + orderId + "]");
            return 1;
        }else{
            logger.error("修改库存失败：[" + orderId + "]");
            return -1;
        }
    }

    public synchronized   int updateGoods( long goodsId, int goodsNumber){

        ShopGoods shopGoods =shopGoodsMapper.selectByPrimaryKey(goodsId);
        Integer goodnumber = shopGoods.getGoodsNumber()-goodsNumber;
        shopGoods.setGoodsNumber(goodnumber);
        if(shopGoodsMapper.updateByPrimaryKey(shopGoods)>=0){
            //logger.info("修改库存成功：[" + goodsId + "]");
            return 1;
        }else{
            logger.error("修改库存失败：[" + goodsId + "]");
            return -1;
        }
    }

    //回退-扣减库存
    public  int CancelupdateGoods(long orderId, long goodsId, int goodsNumber){
        try {
            //去重表中有，才能证明是插入了，所以要回退
            shopGoodsUnique shopGoodsUnique = new shopGoodsUnique();
            shopGoodsUnique.setOrderId(orderId);
            shopGoodsUniqueMapper.insert(shopGoodsUnique);
        }catch (Exception e) {
            ShopGoods shopGoods =shopGoodsMapper.selectByPrimaryKey(goodsId);
            Integer goodnumber = shopGoods.getGoodsNumber()+goodsNumber;
            shopGoods.setGoodsNumber(goodnumber);
            if(shopGoodsMapper.updateByPrimaryKey(shopGoods)>=0){
                //logger.info("修改库存成功：[" + orderId + "]");
                return 1;
            }else{
                logger.error("回退库存失败：[" + orderId + "]");
                return -1;
            }
        }
      return 1;
    }

    //生成订单--把订单信息发送到MQ
    public int MQShopOrder(String ticket) throws Exception {
        Message message = new Message("ticket","",ticket,ticket.getBytes());
        SendResult sendResult = rocketMQTemplate.getProducer().send(message);
        if(sendResult.getSendStatus() == SendStatus.SEND_OK){
            return  1;
        }else{
            return  -1;
        }
    }


}
