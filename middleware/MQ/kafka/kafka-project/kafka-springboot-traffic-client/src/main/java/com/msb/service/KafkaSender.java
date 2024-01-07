package com.msb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 类说明：模拟高并发的Kafka的生产者
 */
@Component
public class KafkaSender {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void messageSender(String tpoic,String key,String message){
        try {
            System.out.println("准备发送..."+tpoic+","+","+key);
            kafkaTemplate.send(tpoic,key,message);
            System.out.println("已发送");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
