package com.msb.selfpartition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * 类说明：自定义分区器，以value值进行分区
 */
public class SelfPartitioner implements Partitioner {
    public int partition(String topic, Object key, byte[] keyBytes,
                         Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int num = partitionInfos.size();
        int parId = Utils.toPositive(Utils.murmur2(valueBytes)) % num;//来自DefaultPartitioner的处理
        return parId;
    }

    public void close() {
        //do nothing
    }

    public void configure(Map<String, ?> configs) {
        //do nothing
    }

}
