package com.msb.redis.adv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

@Component
public class RedisPipeline {

    @Autowired
    private JedisPool jedisPool;

    public List<Object> plGet(List<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //pipe是将所有的命令组装成pipeline
            Pipeline pipelined = jedis.pipelined();
            pipelined.multi();//开启事务
            //。。。。。等等命令
            pipelined.exec();//提交事务
            for(String key:keys){
                pipelined.get(key);//不是仅仅是get方法，set方法还要很多很多方法pipeline都提供了支持
            }
            return pipelined.syncAndReturnAll();//这里只会向redis发送一次
        } catch (Exception e) {
            throw new RuntimeException("执行Pipeline获取失败！",e);
        } finally {
            jedis.close();
        }
    }

    public void plSet(List<String> keys,List<String> values) {
        if(keys.size()!=values.size()) {
            throw new RuntimeException("key和value个数不匹配！");
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipelined = jedis.pipelined();
            for(int i=0;i<keys.size();i++){
                pipelined.set(keys.get(i),values.get(i));
            }
            pipelined.sync();
        } catch (Exception e) {
            throw new RuntimeException("执行Pipeline设值失败！",e);
        } finally {
            jedis.close();
        }
    }
}
