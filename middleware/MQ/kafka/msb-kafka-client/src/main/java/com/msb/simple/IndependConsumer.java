package com.msb.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 类说明：独立消费者
 */
public class IndependConsumer {

    private static KafkaConsumer<String,String> consumer = null;

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"single-consumer");

        /*独立消息消费者*/
        consumer= new KafkaConsumer<String, String>(properties);
        List<TopicPartition> topicPartitionList = new ArrayList<TopicPartition>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor("msb");
        if(null!=partitionInfos){
            for(PartitionInfo partitionInfo:partitionInfos){
                topicPartitionList.add(new TopicPartition(partitionInfo.topic(),
                        partitionInfo.partition()));
            }
        }
        //分配独立消费者的分区
        consumer.assign(topicPartitionList);
        try {

            while(true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, String> record:records){
                    System.out.println(String.format(
                            "主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                            record.topic(),record.partition(),record.offset(),
                            record.key(),record.value()));
                    //do our work
                }
            }
        } finally {
            consumer.close();
        }
    }




}
