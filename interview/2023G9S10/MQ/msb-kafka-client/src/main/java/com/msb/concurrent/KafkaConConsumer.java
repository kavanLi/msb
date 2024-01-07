package com.msb.concurrent;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类说明：多线程下正确的使用消费者，需要记住，一个线程一个消费者
 */
public class KafkaConConsumer {

    public static final int CONCURRENT_PARTITIONS_COUNT = 2;

    private static ExecutorService executorService = Executors.newFixedThreadPool(CONCURRENT_PARTITIONS_COUNT);

    private static class ConsumerWorker implements Runnable{

        private KafkaConsumer<String,String> consumer;

        public ConsumerWorker(Map<String, Object> config, String topic) {
            Properties properties = new Properties();
            properties.putAll(config);
            //一个线程一个消费者
            this.consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Collections.singletonList(topic));
        }

        public void run() {
            final String ThreadName = Thread.currentThread().getName();
            try {
                while(true){
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
                    for(ConsumerRecord<String, String> record:records){
                        System.out.println(ThreadName+"|"+String.format(
                                "主题：%s，分区：%d，偏移量：%d，" +
                                        "key：%s，value：%s",
                                record.topic(),record.partition(),
                                record.offset(),record.key(),record.value()));
                        //do our work
                    }
                }
            } finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {

        /*消费配置的实例*/
        Map<String,Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"c_test");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        for(int i = 0; i<CONCURRENT_PARTITIONS_COUNT; i++){
            //一个线程一个消费者
            executorService.submit(new ConsumerWorker(properties, "concurrent-ConsumerOffsets"));
        }
    }




}
