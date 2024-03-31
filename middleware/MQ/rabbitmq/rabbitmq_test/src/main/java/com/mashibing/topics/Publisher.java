package com.mashibing.topics;

import com.mashibing.util.RabbitMQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @author zjw
 * @description
 * @date 2022/1/25 20:28
 */
public class Publisher {

    public static final String EXCHANGE_NAME = "topic";
    public static final String QUEUE_NAME1 = "topic-one";
    public static final String QUEUE_NAME2 = "topic-two";
    @Test
    public void publish() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //4. 构建队列
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false,false,null);

        //5. 绑定交换机和队列，
        // TOPIC类型的交换机在和队列绑定时，需要以aaa.bbb.ccc..方式编写routingkey
        // 其中有两个特殊字符：*（相当于占位符），#（相当通配符）
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"*.orange.*");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"lazy.#");

        //6. 发消息到交换机
        channel.basicPublish(EXCHANGE_NAME,"big.orange.rabbit",null,"大橙兔子！".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"small.white.rabbit",null,"小白兔".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"lazy.dog.dog.dog.dog.dog.dog",null,"懒狗狗狗狗狗狗".getBytes());
        System.out.println("消息成功发送！");


    }
}
