package cn.edu.config;

import cn.edu.dao.TransactionLogDao;
import cn.edu.model.ShopOrder;
import cn.edu.service.OrderServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderTransactionListener implements TransactionListener {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    TransactionLogDao transactionLogDao;

    /**
     * 发送half msg 返回send ok后调用的方法
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        System.out.println("开始执行本地事务....");
        LocalTransactionState state;
        try{
            //1.解析消息内容
            String body = new String(message.getBody(),"UTF-8");
            //TODO 使用GSON反序列化
            Gson gson = new Gson();
            ShopOrder order = (ShopOrder)gson.fromJson(body, ShopOrder.class);
            //2、插入order表（订单表、事务表）
            orderService.createOrder(order,message.getTransactionId());
            // 同步的处理---返回commit后，消息能被消费者消费
            state = LocalTransactionState.COMMIT_MESSAGE;
            System.out.println("本地事务直接返回成功，这条分布式事务消息就不会触发事务回查！！)："+message.getTransactionId());


            //state = LocalTransactionState.ROLLBACK_MESSAGE;
            //System.out.println("本地事务失败--后续都不走！！)："+message.getTransactionId());
           // 异步的处理（可以满足 高并发需求）
           // state=LocalTransactionState.UNKNOW;
          //  System.out.println("本地事务返回UNKNOW 状态--后续应该走事务回查)："+message.getTransactionId());

        }catch (Exception e){
            System.out.println("执行本地事务失败："+e);
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }

    /**
     * 定时回查的方法
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

        // 回查多次失败 人工补偿。提醒人。发邮件的。
        System.out.println("开始回查本地事务状态："+messageExt.getTransactionId());
        LocalTransactionState state;
        String transactionId = messageExt.getTransactionId();
        //这里如果发现事务表已经插入成功了，那么事务回查  commit
        if (transactionLogDao.selectCount(transactionId)>0){
            state = LocalTransactionState.COMMIT_MESSAGE;
        }else {
            //这里一般不会直接返回失败
            state = LocalTransactionState.UNKNOW;
        }
        System.out.println("结束本地事务状态查询："+state);
        return state;
    }
}