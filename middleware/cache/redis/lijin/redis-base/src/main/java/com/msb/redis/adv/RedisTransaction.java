package com.msb.redis.adv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

@Component
public class RedisTransaction {

    public final static String RS_TRANS_NS = "rts:";

    @Autowired
    private JedisPool jedisPool;

    public List<Object> transaction(String... watchKeys){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(watchKeys.length>0){
                /*使用watch功能*/
                String watchResult = jedis.watch(watchKeys);
                if(!"OK".equals(watchResult)) {
                    throw new RuntimeException("执行watch失败:"+watchResult);
                }
            }
            Transaction multi = jedis.multi();
            multi.set(RS_TRANS_NS+"test1","a1");
            multi.set(RS_TRANS_NS+"test2","a2");
            multi.set(RS_TRANS_NS+"test3","a3");
            List<Object> execResult = multi.exec();
            if(execResult==null){
                throw new RuntimeException("事务无法执行，监视的key被修改:"+watchKeys);
            }
            System.out.println(execResult);
            return execResult;
        } catch (Exception e) {
            throw new RuntimeException("执行Redis事务失败！",e);
        } finally {
            if(watchKeys.length>0){
                jedis.unwatch();/*前面如果watch了，这里就要unwatch*/
            }
            jedis.close();
        }
    }
}
