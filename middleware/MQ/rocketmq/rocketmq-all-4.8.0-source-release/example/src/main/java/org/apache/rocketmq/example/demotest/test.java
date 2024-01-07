package org.apache.rocketmq.example.demotest;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;

public class test {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("ooxx");

        //设置连接服务地址
        producer.setNamesrvAddr("127.0.0.1:9876");
      //  producer.setSendMsgTimeout(8000);
        producer.start();
        Message message = new Message();
        message.setTopic("lz");
        message.setTags("TagA");
        message.setBody(("ooxx").getBytes(StandardCharsets.UTF_8));
        //设置当前的消息需要等待broker存储好之后返回消息
        message.setWaitStoreMsgOK(true);
        //同步发送
        SendResult result = producer.send(message);
        System.out.println(result);

        for (int i = 0; i < 1; i++) {

//            producer.send(message, new SendCallback() {
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult);
//                }
//
//                @Override
//                public void onException(Throwable e) {
//
//                }
//            });
//            producer.sendOneway(message);


            //消息发送到指定的队列中
            MessageQueue mq = new MessageQueue("lz","rocketmq",0);
            SendResult send = producer.send(message, mq);
            System.out.println(send);
        }
    }

}
