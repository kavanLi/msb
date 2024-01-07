package com.msb.interceptor;

import com.msb.selfpartition.SelfPartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * 类说明：发送消息--同步模式
 */
public class SynProducerInterceptor {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的序列化
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        //设置自定义拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, SelfInterceptor.class);

        //设置拦截链路(设置多个 SelfInterceptor  先执行   再执行SelfInterceptor2)
        ArrayList<String> interceptors = new ArrayList<>();
        interceptors.add("com.msb.interceptor.SelfInterceptor");//注意：这里是拦截器的全类名
        interceptors.add("com.msb.interceptor.SelfInterceptor2"); //这里假设有SelfInterceptor2
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);


        // 构建kafka生产者对象
        KafkaProducer<String,String> producer  = new KafkaProducer<String, String>(properties);

        try {
            ProducerRecord<String,String> record;
            try {
                // 构建消息
                record = new ProducerRecord<String,String>("msb", "teacher2333","lijin");
                // 发送消息
                Future<RecordMetadata> future =producer.send(record);
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
