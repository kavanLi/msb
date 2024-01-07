package com.msb.rocket.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.streams.core.RocketMQStream;
import org.apache.rocketmq.streams.core.function.ValueMapperAction;
import org.apache.rocketmq.streams.core.rstream.StreamBuilder;
import org.apache.rocketmq.streams.core.serialization.KeyValueSerializer;
import org.apache.rocketmq.streams.core.topology.TopologyBuilder;
import org.apache.rocketmq.streams.core.util.Pair;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
//运用RocketMQ Stream，将一个消息主题（"sell"）的消息复制到另一个主题（"sell-2"）
public class StreamCopy {
    public static void main(String[] args) {
        //构建流处理实例：一个JobId对应一个StreamBuilder实例
        StreamBuilder builder = new StreamBuilder("StreamCopy-2");
        //定义source topic 和反序列化方式
        builder.source("sell", total -> { //total 参数表示消息的总体内容
                    String value = new String(total, StandardCharsets.UTF_8);
                    return new Pair<>(null, value);
                })
                //sink方法：将结果输出到特定topic
                .sink("sell-2", new KeyValueSerializer<Object, String>() {
                    final ObjectMapper objectMapper = new ObjectMapper();
                    @Override
                    public byte[] serialize(Object o, String data) throws Throwable {
                        return objectMapper.writeValueAsBytes(data);
                    }
                });
        //一个StreamBuilder实例，有一个TopologyBuilder，TopologyBuilder可构建出数据处理器processor
        TopologyBuilder topologyBuilder = builder.build();

        Properties properties = new Properties();
        properties.put(MixAll.NAMESRV_ADDR_PROPERTY, "127.0.0.1:9876");
        //RocketMQStream实例，有一个拓扑构建器TopologyBuilder
        RocketMQStream rocketMQStream = new RocketMQStream(topologyBuilder, properties);
        Runtime.getRuntime().addShutdownHook(new Thread("StreamCopy-shutdown-hook") {
            @Override
            public void run() {
                rocketMQStream.stop();
            }
        });

        rocketMQStream.start();
    }
}
