package com.msb.redis.redismq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class ZSetVer {
    public final static String RS_ZS_MQ_NS = "rzsm:";

    @Autowired
    private JedisPool jedisPool;

    /*生产者，消息的发送，实际生产中，相关参数，
    比如订单信息，过期时间等应该传入，可以考虑将订单信息json化存入redis*/
    public void producer() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            for (int i = 0; i < 5; i++) {
                String order_id = "000000000"+i;
                double score = System.currentTimeMillis()+(i*1000);
                jedis.zadd(RS_ZS_MQ_NS+"orderId",score, order_id);
                System.out.println("生产订单: " + order_id + " 当前时间："
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                System.out.println((3 + i) + "秒后执行");
            }
        } catch (Exception e) {
            throw new RuntimeException("生产消息失败！");
        } finally {
            jedis.close();
        }

    }

    //消费者，取订单
    public void consumerDelayMessage() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            while (true) {
                Set<String> order = jedis.zrangeByScore(RS_ZS_MQ_NS+"orderId", 0,
                        System.currentTimeMillis(), 0,1);
                if (order == null || order.isEmpty()) {
                    System.out.println("当前没有等待的任务");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                String s = order.iterator().next();

                if (jedis.zrem(RS_ZS_MQ_NS+"orderId", s)>0) {
                    /*业务处理*/
                    System.out.println(s);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("消费消息失败！");
        } finally {
            jedis.close();
        }
    }
}
