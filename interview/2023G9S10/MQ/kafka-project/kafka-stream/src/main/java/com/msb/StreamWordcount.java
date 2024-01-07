package com.msb;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * 类说明：使用Stream做统计，且加过滤
 * 将sell中的数据实现wordcount（不同牌子手机的数量，除了iphone）写入到wordcount-output
 */
public class StreamWordcount {

    public static void main(String[] args) throws Exception{

        // 设置属性
        Properties properties = new Properties();
        /*每个stream应用都必须有唯一的id*/
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-2");
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        properties.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG,2000);  //提交时间设置为2秒
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass()); //输入key的类型
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass());  //输入value的类型

        //创建流构造器
        StreamsBuilder builder = new StreamsBuilder();
        KTable<String, Long> count = builder.stream("sell") //从kafka中一条一条取数据
                .flatMapValues(                //返回压扁后的数据
                        (value) -> {           //对数据按空格进行切割，返回List集合
                            String[] split = value.toString().split(" ");
                            List<String> strings = Arrays.asList(split);
                            return strings;
                        })
                .map((k, v) -> { return new KeyValue<String, String>(v,v); })
                .filter((key, value) -> (!value.equals("iphone")))//过滤掉iphone
                .groupByKey().count();
            count.toStream().foreach((k,v)->{
            System.out.println("key:"+k+"   "+"value:"+v);
        });

        count.toStream().map((x,y)->{
            return new KeyValue<String,String>(x,y.toString());  //注意转成toString类型，我们前面设置的kv的类型都是string类型
        }).to("wordcount-output");

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
            streams.start();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
