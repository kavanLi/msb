package com.msb.redis.redisbase.adv;

import com.msb.redis.adv.RedisPipeline;
import com.msb.redis.redisbase.basetypes.RedisString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestRedisPipeline {

    @Autowired
    private RedisPipeline redisPipeline;
    @Autowired
    private RedisString redisString;

    private static final int TEST_COUNT = 10000;

    @Test
    public void testPipeline() {
        long setStart = System.currentTimeMillis();
        for (int i = 0; i < TEST_COUNT; i++) { //单个的操作
            redisString.set("testStringM:key_" + i, String.valueOf(i));
        }
        long setEnd = System.currentTimeMillis();
        System.out.println("非pipeline操作"+TEST_COUNT+"次字符串数据类型set写入，耗时：" + (setEnd - setStart) + "毫秒");

        List<String> keys = new ArrayList<>(TEST_COUNT);
        List<String> values= new ArrayList<>(TEST_COUNT);
        for (int i = 0; i < keys.size(); i++) {
            keys.add("testpipelineM:key_"+i);
            values.add(String.valueOf(i));
        }
        long pipelineStart = System.currentTimeMillis();
        redisPipeline.plSet(keys,values);
        long pipelineEnd = System.currentTimeMillis();
        System.out.println("pipeline操作"+TEST_COUNT+"次字符串数据类型set写入，耗时：" + (pipelineEnd - pipelineStart) + "毫秒");
    }

}
