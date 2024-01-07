package com.msb.rebalance;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类说明：再均衡监听器
 */
public class HandlerRebalance implements ConsumerRebalanceListener {

    /*模拟一个保存分区偏移量的数据库表*/
    public final static ConcurrentHashMap<TopicPartition,Long>
            partitionOffsetMap = new ConcurrentHashMap<TopicPartition,Long>();

    private final Map<TopicPartition, OffsetAndMetadata> currOffsets;
    private final KafkaConsumer<String,String> consumer;
    //private final Transaction  tr事务类的实例

    public HandlerRebalance(Map<TopicPartition, OffsetAndMetadata> currOffsets,
                            KafkaConsumer<String, String> consumer) {
        this.currOffsets = currOffsets;
        this.consumer = consumer;
    }

    //分区再均衡之前
    public void onPartitionsRevoked(
            Collection<TopicPartition> partitions) {
        final String id = Thread.currentThread().getId()+"";
        System.out.println(id+"-onPartitionsRevoked参数值为："+partitions);
        System.out.println(id+"-服务器准备分区再均衡，提交偏移量。当前偏移量为："
                +currOffsets);
        //我们可以不使用consumer.commitSync(currOffsets);
        //提交偏移量到kafka,由我们自己维护*/
        //开始事务
        //偏移量写入数据库
        System.out.println("分区偏移量表中："+partitionOffsetMap);
        for(TopicPartition topicPartition:partitions){
            partitionOffsetMap.put(topicPartition, currOffsets.get(topicPartition).offset());
        }
        consumer.commitSync(currOffsets);
        //提交业务数和偏移量入库  tr.commit
    }

    //分区再均衡完成以后
    public void onPartitionsAssigned(
            Collection<TopicPartition> partitions) {
        final String id = Thread.currentThread().getId()+"";
        System.out.println(id+"-再均衡完成，onPartitionsAssigned参数值为："+partitions);
        System.out.println("分区偏移量表中："+partitionOffsetMap);
        for(TopicPartition topicPartition:partitions){
            System.out.println(id+"-topicPartition"+topicPartition);
            //模拟从数据库中取得上次的偏移量
            Long offset = partitionOffsetMap.get(topicPartition);
            if(offset==null) continue;
            consumer.seek(topicPartition,partitionOffsetMap.get(topicPartition));
        }

    }
}
