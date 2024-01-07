package com.msb.simple;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaMessageConsumer {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        //properties.put("bootstrap.servers", "localhost:9092");
        properties.put("bootstrap.servers", "192.168.0.201:9092");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", StringDeserializer.class.getName());
        properties.put("group.id", "ConsumerOffsets");

        // 创建 Kafka 消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // 订阅主题
        consumer.subscribe(Collections.singletonList("msb"));

        try {
            while (true) {
                // 拉取消息
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                // 处理消息
                records.forEach(record -> {
                    System.out.println("Received message. Topic: " + record.topic()
                            + ", Partition: " + record.partition()
                            + ", Offset: " + record.offset()
                            + ", Key: " + record.key()
                            + ", Value: " + record.value());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
}
