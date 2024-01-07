package com.msb.selfserial;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 类说明：
 */
public class ConsumerUser {


    public static void main(String[] args) {

        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        // 设置自定义的反序列化
        properties.put("value.deserializer", UserDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"ConsumerOffsets");
        // 构建kafka消费者对象
        KafkaConsumer<String,User> consumer = new KafkaConsumer<String, User>(properties);
        try {
            consumer.subscribe(Collections.singletonList("msb-user"));
            // 调用消费者拉取消息
            while(true){
                // 每隔1秒拉取一次消息
                ConsumerRecords<String, User> records= consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, User> record:records){
                    String key = record.key();
                    User user = record.value();
                    System.out.println("接收到消息: key = " + key + ", value = " + user.toString());
                }
            }
        } finally {
            // 释放连接
            consumer.close();
        }
    }




}
