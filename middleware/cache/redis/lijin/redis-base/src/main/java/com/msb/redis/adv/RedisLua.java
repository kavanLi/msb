package com.msb.redis.adv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;

/*基于redis的一个限流功能*/
@Component
public class RedisLua {

    public final static String RS_LUA_NS = "rlilf:";
    /*第一次使用incr对KEY（某个IP作为KEY）加一，如果是第一次访问，
    使用expire设置一个超时时间，这个超时时间作为Value第一个参数传入，
    如果现在递增的数目大于输入的第二个Value参数，返回失败标记，否则成功。
    redis的超时时间到了，这个Key消失，又可以访问
        local num = redis.call('incr', KEYS[1])
        if tonumber(num) == 1 then
            redis.call('expire', KEYS[1], ARGV[1])
            return 1
        elseif tonumber(num) > tonumber(ARGV[2]) then
            return 0
        else
            return 1
        end
    * */
    public final static String LUA_SCRIPTS =
            "local num = redis.call('incr', KEYS[1])\n" +
            "if tonumber(num) == 1 then\n" +
            "\tredis.call('expire', KEYS[1], ARGV[1])\n" +
            "\treturn 1\n" +
            "elseif tonumber(num) > tonumber(ARGV[2]) then\n" +
            "\treturn 0\n" +
            "else \n" +
            "\treturn 1\n" +
            "end";

    @Autowired
    private JedisPool jedisPool;

    public String loadScripts(){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String sha =jedis.scriptLoad(LUA_SCRIPTS);
            return sha;
        } catch (Exception e) {
            throw new RuntimeException("加载脚本失败！",e);
        } finally {
            jedis.close();
        }
    }

    public String ipLimitFlow(String ip){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = jedis.evalsha("9ac7623ae2435baf9ebf3ef4d21cde13de60e85c",
                    Arrays.asList(RS_LUA_NS+ip),Arrays.asList("60","2")).toString();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("执行脚本失败！",e);
        } finally {
            jedis.close();
        }
    }


}
