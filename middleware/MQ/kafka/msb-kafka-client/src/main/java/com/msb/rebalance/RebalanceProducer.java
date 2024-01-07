package com.msb.rebalance;

import com.msb.selfserial.User;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 类说明：多线程下使用生产者
 */
public class RebalanceProducer {
    //发送消息的个数
    private static final int MSG_SIZE = 50;
    //负责发送消息的线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    private static CountDownLatch countDownLatch  = new CountDownLatch(MSG_SIZE);

    private static User makeUser(int id){
        User user = new User(id);
        String userName = "msb_"+id;
        user.setName(userName);
        return user;
    }

    /*发送消息的任务*/
    private static class ProduceWorker implements Runnable{

        private ProducerRecord<String,String> record;
        private KafkaProducer<String,String> producer;

        public ProduceWorker(ProducerRecord<String, String> record, KafkaProducer<String, String> producer) {
            this.record = record;
            this.producer = producer;
        }

        public void run() {
            final String ThreadName = Thread.currentThread().getName();
            try {
                producer.send(record, new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if(null!=exception){
                            exception.printStackTrace();
                        }
                        if(null!=metadata){
                            System.out.println(ThreadName+"|" +String.format("偏移量：%s,分区：%s", metadata.offset(),
                                    metadata.partition()));
                        }
                    }
                });
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 设置属性
        Properties properties = new Properties();
        // 指定连接的kafka服务器的地址
        properties.put("bootstrap.servers","127.0.0.1:9092");
        // 设置String的序列化
        properties.put("key.serializer", StringSerializer.class);
        properties.put("value.serializer", StringSerializer.class);
        // 构建kafka生产者对象
        KafkaProducer<String,String> producer  = new KafkaProducer<String, String>(properties);
        try {
            for(int i=0;i<MSG_SIZE;i++){
                User user = makeUser(i);
                ProducerRecord<String,String> record = new ProducerRecord<String,String>("rebalance",null,
                        System.currentTimeMillis(), user.getId()+"", user.toString());
                executorService.submit(new RebalanceProducer.ProduceWorker(record,producer));
                Thread.sleep(600);
            }
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
            executorService.shutdown();
        }
    }


}
