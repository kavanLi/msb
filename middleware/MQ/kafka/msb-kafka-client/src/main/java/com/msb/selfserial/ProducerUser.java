package com.msb.selfserial;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 类说明：发送消息--value使用自定义序列化
 */
public class ProducerUser {

    public static void main(String[] args) {


        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的序列化
        properties.put("key.serializer", StringSerializer.class);
        // 设置value的自定义序列化
        properties.put("value.serializer", UserSerializer.class);

        // 构建kafka生产者对象
        KafkaProducer<String,User> producer  = new KafkaProducer<String, User>(properties);
        try {
            ProducerRecord<String,User> record;
            try {
                // 构建消息
                record = new ProducerRecord<String,User>("msb-user", "teacher",new User(1,"lijin"));
                // 发送消息
                producer.send(record);
                System.out.println("message is sent.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            // 释放连接
            producer.close();
        }
    }
}
