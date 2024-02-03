package com.msb.redis.redisbase.redismq;

import com.msb.redis.redismq.StreamVer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.StreamEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestStreamVer {

    @Autowired
    private StreamVer streamVer;

    private final static String KEY_NAME = "testStream";
    private final static String GROUP_NAME = "testgroup";

    @Test
    void testProduce(){
        Map<String,String> message = new HashMap<>();
        message.put("name","lijin");
        message.put("age","18");
        streamVer.produce(KEY_NAME,new HashMap<>(message));//生产
        streamVer.MqInfo(StreamVer.MQ_INFO_STREAM,KEY_NAME,null); //查看
        streamVer.MqInfo(StreamVer.MQ_INFO_GROUP,KEY_NAME,null);
    }

    @Test
    void testConsumer(){
        if (!streamVer.checkGroup(KEY_NAME,GROUP_NAME)){
            streamVer.createCustomGroup(KEY_NAME,GROUP_NAME,null);
        }
        List<Map.Entry<String, List<StreamEntry>>> results = streamVer.consume(KEY_NAME,"testUser",GROUP_NAME);
        streamVer.MqInfo(StreamVer.MQ_INFO_GROUP,KEY_NAME,GROUP_NAME);
        streamVer.MqInfo(StreamVer.MQ_INFO_CONSUMER,KEY_NAME,GROUP_NAME);
        for(Map.Entry<String, List<StreamEntry>> result:results ){
            for(StreamEntry entry:result.getValue()){
                streamVer.ackMsg(KEY_NAME,GROUP_NAME,entry.getID());
                streamVer.MqInfo(StreamVer.MQ_INFO_GROUP,KEY_NAME,GROUP_NAME);
                streamVer.MqInfo(StreamVer.MQ_INFO_CONSUMER,KEY_NAME,GROUP_NAME);
            }

        }
    }

    @Test
    void testAck(){
        streamVer.ackMsg(KEY_NAME,GROUP_NAME,null);
        streamVer.MqInfo(StreamVer.MQ_INFO_GROUP,KEY_NAME,GROUP_NAME);
        streamVer.MqInfo(StreamVer.MQ_INFO_CONSUMER,KEY_NAME,GROUP_NAME);
    }

}
