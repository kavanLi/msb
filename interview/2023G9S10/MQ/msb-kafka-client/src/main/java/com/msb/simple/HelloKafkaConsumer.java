package com.msb.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 类说明：消费者入门
 */
public class HelloKafkaConsumer {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"ConsumerOffsets");
        // 构建kafka消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        try {
            consumer.subscribe(Collections.singletonList("msb"));
            // 调用消费者拉取消息
            while(true){
                // 设置1秒的超时时间
                ConsumerRecords<String, String> records= consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, String> record:records){
                    String key = record.key();
                    String value = record.value();
                    System.out.println("接收到消息: key = " + key + ", value = " + value);
                }
            }
        } finally {
            // 释放连接
            consumer.close();

        }

    }




}
