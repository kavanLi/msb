package com.msb.commit;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 类说明：消费者 手动提交：  自定义的方式
 */
public class ConsumerSpecial {

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"ConsumerOffsets");
        /*取消自动提交*/
        properties.put("enable.auto.commit",false);

        // 构建kafka消费者对象
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        //当前偏移量
        Map<TopicPartition, OffsetAndMetadata> currOffsets = new HashMap<TopicPartition, OffsetAndMetadata>();
        int count = 0;
        try {
            consumer.subscribe(Collections.singletonList("msb"));
            // 调用消费者拉取消息
            while(true){
                // 每隔1秒拉取一次消息
                ConsumerRecords<String, String> records= consumer.poll(Duration.ofSeconds(1));
                for(ConsumerRecord<String, String> record:records){
                    String key = record.key();
                    String value = record.value();
                    System.out.println("接收到消息: key = " + key + ", value = " + value);
                    currOffsets.put(new TopicPartition(record.topic(),record.partition()),
                            new OffsetAndMetadata(record.offset()+1,"no meta"));
                    count++;
                    if(count%10==0){ //每10条提交一次（特定的偏移量）
                        consumer.commitAsync(currOffsets,null);
                    }

                }
            }
        }catch (CommitFailedException e) {
            System.out.println("Commit failed:");
            e.printStackTrace();
        }finally {
            try {
                consumer.commitSync(currOffsets);//同步提交(这里也可以)
            } finally {
                consumer.close();
            }
        }

    }




}
