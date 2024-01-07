package com.msb;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

/**
 * 类说明：
 */
public class SellProduct {

    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers","127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /*消息生产者*/
        producer = new KafkaProducer<String, String>(properties);
        String[] words = {"iphone","huawei","xiaomi","oppo","vivo"};
        Random r = new Random();
        Random r1 = new Random();
        try {
            ProducerRecord<String,String> record;
            /*待发送的消息实例*/
            while(true){
                int wordCount = r.nextInt(5);
                if (wordCount==0) continue;
                StringBuilder sb = new StringBuilder("");
                for(int i=0;i<wordCount;i++){
                    sb.append(words[r1.nextInt(words.length)]).append(" ");
                }
                try {
                    record = new ProducerRecord<String,String>("sell","product",sb.toString());
                    producer.send(record);
                    System.out.println("购买的商品为："+sb.toString());
                    Thread.sleep(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            producer.close();
        }
    }




}
