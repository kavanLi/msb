package cn.edu.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionProducer {
    
    private String producerGroup = "order_trans_group";

    // 事务消息
    private TransactionMQProducer producer;

    //用于执行本地事务和事务状态回查的监听器
    @Autowired
    OrderTransactionListener orderTransactionListener;
    //执行（定时回查）任务的线程池
    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 60,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(50));
    //这个注解会在项目的启动时调用
    @PostConstruct
    public void init(){
        producer = new TransactionMQProducer(producerGroup);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(Integer.MAX_VALUE);
        producer.setExecutorService(executor);  //设置事务回查 回调的线程池
        producer.setTransactionListener(orderTransactionListener); //分布式事务的监听类
        this.start();
    }
    private void start(){
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }
    //事务消息发送 
    public TransactionSendResult send(String data, String topic) throws MQClientException {
        Message message = new Message(topic,data.getBytes());
        return this.producer.sendMessageInTransaction(message, null);  //进行半事务消息发送方
    }
}