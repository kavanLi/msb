package com.msb.consumergroup;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 类说明：消费者入门:自动提交的消费模式
 */
public class GroupBConusmer1 {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"groupB");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");  //最早：从头开始消费

       // properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest"); // 已提交的offset开始消费


        // 构建kafka消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        try {
            consumer.subscribe(Collections.singletonList("msb"));
            // 调用消费者拉取消息
            while(true){
                // 每隔1秒拉取一次消息
                ConsumerRecords<String, String> records= consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, String> record:records){
                    String key = record.key();
                    String value = record.value();
                    int partition =  record.partition();
                    System.out.println("接收到消息:"+",partition ="+partition +", key = " + key + ", value = " + value);
                }
            }
        } finally {
            // 释放连接
            consumer.close();

        }

    }




}
