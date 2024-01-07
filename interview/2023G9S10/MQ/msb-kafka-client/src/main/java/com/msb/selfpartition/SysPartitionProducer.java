package com.msb.selfpartition;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.internals.DefaultPartitioner;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 类说明：默认分区器，key值
 */
public class SysPartitionProducer {

    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的序列化
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        // 默认分区器
        //properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class);
        //轮询分区器
       // properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);
       // properties.put("partitioner.availability.timeout.ms", "0");
       // properties.put("partitioner.ignore.keys", "true");
        //统一粘性分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, UniformStickyPartitioner.class);

        // 构建kafka生产者对象
        KafkaProducer<String,String> producer  = new KafkaProducer<String, String>(properties);
        try {
            ProducerRecord<String,String> record;
            try {
                 for (int i =0;i<10;i++){  //发10条消息
                     // 构建消息
                     // record = new ProducerRecord<String,String>("msb", "teacher","lijin");
                     // 指定partition
                      record = new ProducerRecord<String,String>("msb", 0,"teacher"+i,"lijin"+i);
                     // 发送消息
                     Future<RecordMetadata> future =producer.send(record);
                     RecordMetadata recordMetadata = future.get();
                     if(null!=recordMetadata){
                         System.out.println(i+","+"offset:"+recordMetadata.offset()+","
                                 +"partition:"+recordMetadata.partition());
                     }
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
