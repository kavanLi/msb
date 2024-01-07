package com.msb.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 类说明：使用Kafka提供服务，实现（削峰填谷）流量整形
 *
 */
@Component
public class ConsumerService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static ExecutorService executorService
            = new ThreadPoolExecutor(2,2,
            60,TimeUnit.SECONDS,new SynchronousQueue<>());
    private static List<KafkaConsumer> consumers
            = new ArrayList<KafkaConsumer>();
    @Autowired
    private DBService dbService;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${kafka.consumer.servers}")
    private String servers;
    @Value("${kafka.consumer.enable.auto.commit}")
    private boolean enableAutoCommit;
    @Value("${kafka.consumer.session.timeout}")
    private String sessionTimeout;
    @Value("${kafka.consumer.auto.commit.interval}")
    private String autoCommitInterval;
    @Value("${kafka.consumer.group.id}")
    private String groupId;
    @Value("${kafka.consumer.auto.offset.reset}")
    private String autoOffsetReset;
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;

    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        /*控制消费流量，每次只取10条进行消费*/
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,10);
        return propsMap;
    }

    private static class ConsumerWorker implements Runnable{

        private KafkaConsumer<String,String> consumer;
        private DBService dbService;
        private KafkaTemplate kafkaTemplate;

        public ConsumerWorker(KafkaConsumer<String, String> consumer,
                              DBService dbService,
                              KafkaTemplate kafkaTemplate) {
            this.consumer = consumer;
            this.dbService = dbService;
            this.kafkaTemplate = kafkaTemplate;
            /*因为连接池为2个大小，所以限定主题为2个分区，所以消费者也只有2个*/
            consumer.subscribe(Collections.singletonList("traffic-shaping"));
        }

        public void run() {
            final String id = Thread.currentThread().getId()
                    +"-"+System.identityHashCode(consumer);
            try {
                while(true){
                    ConsumerRecords<String, String> records
                            = consumer.poll(100);
                    for(ConsumerRecord<String, String> record:records){
//                        System.out.println(id+"|"+String.format(
//                                "主题：%s，分区：%d，偏移量：%d，" +
//                                        "key：%s，value：%s",
//                                record.topic(),record.partition(),
//                                record.offset(),record.key(),record.value()));
//                        System.out.println("开始购票业务－－－－－－");
                        String result = dbService.useDb("select ticket");
//                        System.out.println(result+"，准备通知客户端");
                        /*主题为10个分区大小，可以更大，因为客户端那边没有削峰的需要，
                        如果需要，一样处理即可*/
                        kafkaTemplate.send("traffic-shaping-result",result);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @PostConstruct
    public void init(){
        for(int i=0;i<2;i++){
            /*启动2个消费者*/
            KafkaConsumer<String,String> consumer
                    = new KafkaConsumer<String,String>(consumerConfigs());
            executorService.submit(new ConsumerWorker(
                    consumer,
                    dbService,
                    kafkaTemplate));
            consumers.add(consumer);
        }
    }

    @PreDestroy
    public void destory(){
        for(KafkaConsumer consumer:consumers){
            consumer.close();
        }
    }


}
