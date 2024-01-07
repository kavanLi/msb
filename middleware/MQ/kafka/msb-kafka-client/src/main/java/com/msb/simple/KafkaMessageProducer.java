package com.msb.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        //properties.put("bootstrap.servers", "localhost:9092");
        properties.put("bootstrap.servers", "192.168.0.201:9092");
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", StringSerializer.class.getName());

        // 创建 Kafka 生产者
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        try {
            // 构建消息
            ProducerRecord<String, String> record = new ProducerRecord<>("msb", "your_key", "4545");

            // 发送消息
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("Message sent successfully. Topic: " + metadata.topic()
                            + ", Partition: " + metadata.partition()
                            + ", Offset: " + metadata.offset());
                } else {
                    System.err.println("Error sending message: " + exception.getMessage());
                }
            });

            // 刷新并关闭生产者
            producer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}