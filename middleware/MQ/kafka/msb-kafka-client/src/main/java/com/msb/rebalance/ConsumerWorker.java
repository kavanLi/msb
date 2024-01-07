package com.msb.rebalance;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 类说明：消费者任务
 */
public class ConsumerWorker implements Runnable{

    private final KafkaConsumer<String,String> consumer;
    /*用来保存每个消费者当前读取分区的偏移量*/
    private final Map<TopicPartition, OffsetAndMetadata> currOffsets;
    private final boolean isStop;

    public ConsumerWorker(boolean isStop) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的反序列化
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,RebalanceConsumer.GROUP_ID);
        /*取消自动提交*/
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        this.isStop = isStop;
        this.consumer = new KafkaConsumer<String, String>(properties);
        this.currOffsets = new HashMap<TopicPartition, OffsetAndMetadata>();  //保存  每个分区的消费偏移量
        System.out.println("consumer-hashcode:"+consumer.hashCode());
        consumer.subscribe(Collections.singletonList("rebalance"), new HandlerRebalance(currOffsets,consumer));
    }

    public void run() {
        final String id = Thread.currentThread().getId()+"";
        int count = 0;
        TopicPartition topicPartition = null;
        long offset = 0;
        try {
            while(true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                //业务处理
                //开始事务
                for(ConsumerRecord<String, String> record:records){
                    System.out.println(id+"|"+String.format(
                            "处理主题：%s，分区：%d，偏移量：%d，" +
                                    "key：%s，value：%s",
                            record.topic(),record.partition(),
                            record.offset(),record.key(),record.value()));
                    topicPartition = new TopicPartition(record.topic(), record.partition());
                    offset = record.offset()+1;
                    //获取偏移量
                    currOffsets.put(topicPartition,new OffsetAndMetadata(offset, "no"));
                    count++;
                    //执行业务sql
                }
                if(currOffsets.size()>0){
                    for(TopicPartition topicPartitionkey:currOffsets.keySet()){
                        HandlerRebalance.partitionOffsetMap.put(topicPartitionkey, currOffsets.get(topicPartitionkey).offset());
                    }
                    //提交事务,同时将业务和偏移量入库（使用HashMap替代）
                }
                if(isStop&&count>=5){ //监听线程
                    System.out.println(id+"-将关闭，当前偏移量为："+currOffsets);
                    consumer.commitSync();
                    break;  //跳出这个循环
                }
                consumer.commitSync();
                //TODO 优雅退出
               //  consumer.wakeup();
            }
        } finally {
            consumer.close();
        }
    }
}
