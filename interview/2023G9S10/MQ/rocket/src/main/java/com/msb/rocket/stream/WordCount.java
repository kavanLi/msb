package com.msb.rocket.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.streams.core.RocketMQStream;
import org.apache.rocketmq.streams.core.function.ValueMapperAction;
import org.apache.rocketmq.streams.core.rstream.StreamBuilder;
import org.apache.rocketmq.streams.core.serialization.KeyValueSerializer;
import org.apache.rocketmq.streams.core.topology.TopologyBuilder;
import org.apache.rocketmq.streams.core.util.Pair;
import org.apache.rocketmq.streams.core.util.Utils; // 引入 PrintUtil 类

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
//对消息内容进行词频统计
public class WordCount {
    public static void main(String[] args) {
        //构建流处理实例：一个JobId对应一个StreamBuilder实例
        StreamBuilder builder = new StreamBuilder("wordCount-lijin");
        //定义source topic 和反序列化方式
        builder.source("sourceTopic", total -> {
                    String value = new String(total, StandardCharsets.UTF_8);
                    return new Pair<>(null, value);
                })
                //flatMap:对数据进行一对多转化
                .flatMap((ValueMapperAction<String, List<String>>) value -> {
                    String[] splits = value.toLowerCase().split("\\W+");
                    return Arrays.asList(splits);
                })
                //keyBy:按照特定字段分组
                .keyBy(value -> value)
                //count:统计含有某个字段数据的个数
                .count()
                //toRStream:转化为RStream，只是在接口形式上转化，对数据无任何操作
                .toRStream()

               // sink:按照自定义序列化形式将结果写出到topic
                .sink("sell-count", (KeyValueSerializer) (key, value) -> {
                    System.out.println("1---------------:"+key);
                    System.out.println("2---------------:"+value);
                    //return value.toString().getBytes();
                    return ( key+":"+ value).toString().getBytes(); // 将词频统计结果转换为字符串
                });


        TopologyBuilder topologyBuilder = builder.build();

        Properties properties = new Properties();
        properties.put(MixAll.NAMESRV_ADDR_PROPERTY, "127.0.0.1:9876");

        //一个StreamBuilder实例，有一个TopologyBuilder，TopologyBuilder可构建出数据处理器processor
        RocketMQStream rocketMQStream = new RocketMQStream(topologyBuilder, properties);


        final CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("wordcount-shutdown-hook") {
            @Override
            public void run() {
                rocketMQStream.stop();
                latch.countDown();
            }
        });

        try {
            rocketMQStream.start();
            latch.await();
        } catch (final Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
