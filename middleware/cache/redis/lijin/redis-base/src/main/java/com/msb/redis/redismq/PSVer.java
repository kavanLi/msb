package com.msb.redis.redismq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/*
* 基于PUBSUB的消息中间件的实现
* */
@Component
public class PSVer extends JedisPubSub {
    public final static String RS_PS_MQ_NS = "rpsm:";

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("Accept "+channel+" message:"+message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("Subscribe "+channel+" count:"+subscribedChannels);
    }

    public void pub(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(RS_PS_MQ_NS+channel,message);
            System.out.println("发布消息到"+RS_PS_MQ_NS+channel+" message="+message);
        } catch (Exception e) {
            throw new RuntimeException("发布消息失败！");
        } finally {
            jedis.close();
        }
    }

    public void sub(String... channels) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(this,channels);
        } catch (Exception e) {
            throw new RuntimeException("订阅频道失败！");
        } finally {
            jedis.close();
        }
    }

}
