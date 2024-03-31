package com.mashibing.rpc;

import com.mashibing.helloworld.Publisher;
import com.mashibing.util.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zjw
 * @description
 * @date 2022/1/24 23:02
 */
public class Consumer {

    public static final String QUEUE_PUBLISHER = "rpc_publisher";
    public static final String QUEUE_CONSUMER = "rpc_consumer";

    @Test
    public void consume() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(QUEUE_PUBLISHER,false,false,false,null);
        channel.queueDeclare(QUEUE_CONSUMER,false,false,false,null);


        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者获取到消息：" + new String(body,"UTF-8"));
                String resp = "获取到了client发出的请求，这里是响应的信息";
                String respQueueName = properties.getReplyTo();
                String uuid = properties.getCorrelationId();
                AMQP.BasicProperties props = new AMQP.BasicProperties()
                        .builder()
                        .correlationId(uuid)
                        .build();
                channel.basicPublish("",respQueueName,props,resp.getBytes());
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_PUBLISHER,false,callback);
        System.out.println("开始监听队列");

        System.in.read();
    }

}
