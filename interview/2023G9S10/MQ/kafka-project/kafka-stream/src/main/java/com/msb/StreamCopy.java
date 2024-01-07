package com.msb;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

/**
 * 类说明：使用Stream做纯复制
 */
public class StreamCopy {

    public static void main(String[] args) throws Exception{
        // 设置属性
        Properties properties = new Properties();
        /*每个stream应用都必须有唯一的id*/
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "copy");
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass()); //输入key的类型
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());  //输入value的类型


        //创建流构造器  StreamsBuilder 它就是一个数据流
        StreamsBuilder builder = new StreamsBuilder();

        //构建好builder，将sell中的数据写入到sell-2中
        builder.stream("sell").to("sell-2");

        final Topology topo=builder.build();
        final KafkaStreams streams = new KafkaStreams(topo, properties);
        final CountDownLatch latch = new CountDownLatch(1);
        Runtime.getRuntime().addShutdownHook(new Thread("stream"){
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });
        try {
            //这里才是开始进行流计算
            streams.start();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
