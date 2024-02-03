package com.msb.redis.redisbase.redismq;

import com.msb.redis.redismq.ZSetVer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestZSetVer {

    @Autowired
    private ZSetVer zSetVer;

    @Test
    void testConsumerDelayMessage(){
        zSetVer.consumerDelayMessage();
    }

    @Test
    void testProducer(){
        zSetVer.producer();
    }

}
