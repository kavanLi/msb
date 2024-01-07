package com.msb.test;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 类说明：解决消息挤压，作为消费者，同时也作为生产者
 */
public class ConusmerAndProducer {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"groupA");
        // 构建kafka消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        /*独立消息消费者*/
        consumer= new KafkaConsumer<String, String>(properties);
        List<TopicPartition> topicPartitionList = new ArrayList<TopicPartition>();
        List<PartitionInfo> partitionInfos = consumer.partitionsFor("msb");//原来挤压的主题
        if(null!=partitionInfos){
            for(PartitionInfo partitionInfo:partitionInfos){
                //这里你是哪个分区挤压，需要处理一下
                if(partitionInfo.partition() ==1){  //这里是1号分区
                    topicPartitionList.add(new TopicPartition(partitionInfo.topic(),
                            partitionInfo.partition()));
                }
            }
        }
        //分配独立消费者的分区--只消费1号分区
        consumer.assign(topicPartitionList);
        try {
            // 调用消费者拉取消息
            while(true){
                // 每隔1秒拉取一次消息
                ConsumerRecords<String, String> records= consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, String> record:records){
                    String key = record.key();
                    String value = record.value();
                    int partition =  record.partition();
                    System.out.println("接收到消息:"+",partition ="+partition +", key = " + key + ", value = " + value);
                    //这里有一个处理：就是需要 丛 value中拿到  顾客id
                     key ="顾客id";//这里看是伪造数据
                    // 构建kafka生产者对象
                    KafkaProducer<String,String> producer  = new KafkaProducer<String, String>(properties);
                    try {
                        try {
                            // 构建消息--送到msb_2  主题，这个主题应该做多个分区，确保可以多个消费者同时消费
                            ProducerRecord<String,String> record2 = new ProducerRecord<String,String>("msb_2", key,value);
                            // 发送消息
                            Future<RecordMetadata> future =producer.send(record2);
                            RecordMetadata recordMetadata = future.get();
                            if(null!=recordMetadata){
                                System.out.println("offset:"+recordMetadata.offset()+","
                                        +"partition:"+recordMetadata.partition());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } finally {
                        // 释放连接
                        producer.close();
                    }

                }
            }
        } finally {
            // 释放连接
            consumer.close();

        }

    }




}
