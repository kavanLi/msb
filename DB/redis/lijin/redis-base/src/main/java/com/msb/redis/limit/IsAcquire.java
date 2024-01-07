package com.msb.redis.limit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * 分布式限流的服务类
 */
@Service
public class IsAcquire {
    //引入一个Redis的Lua脚本的支持
    private DefaultRedisScript<Long> getRedisScript;



    //判断限流方法---类似于RateLimiter
    public boolean acquire(String limitKey,int limit,int expire) throws  Exception{
        //连接Redis
        Jedis jedis =  new Jedis("127.0.0.1",6379);
        getRedisScript =new  DefaultRedisScript<>();
        getRedisScript.setResultType(Long.class);//脚本执行返回值 long
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimiter.lua")));
        Long result = (Long)jedis.eval(getRedisScript.getScriptAsString(),
                1,limitKey,String.valueOf(limit),String.valueOf(expire));
        if(result ==0){
            return false;
        }
        return true;
    }
}
