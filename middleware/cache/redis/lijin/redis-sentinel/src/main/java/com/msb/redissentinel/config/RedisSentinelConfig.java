package com.msb.redissentinel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:application.properties")
public class RedisSentinelConfig {
    @Value("${redis.sentinel}")
    private String hosts;

    @Value("${redis.master}")
    private String master;

    @Value("${redis.timeout}")
    private int timeout;

    @Value("${redis.maxIdle}")
    private int maxIdle;

    @Value("${redis.maxWaitMillis}")
    private int maxWaitMillis;

    @Value("${redis.blockWhenExhausted}")
    private Boolean blockWhenExhausted;

    @Value("${redis.JmxEnabled}")
    private Boolean JmxEnabled;

    @Bean
    public JedisPoolConfig  jedisPoolConfigFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(JmxEnabled);

        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        return jedisPoolConfig;
    }

    @Bean
    public JedisSentinelPool JedisSentinelPoolFactory(JedisPoolConfig jedisPoolConfig){

        Set<String> nodeSet = new HashSet<>();
        //获取到节点信息
        String nodeString = hosts;
        //判断字符串是否为空
        if(nodeString == null || "".equals(nodeString)){
            throw new RuntimeException("RedisSentinelConfiguration initialize error nodeString is null");
        }
        String[] nodeArray = nodeString.split(",");
        //判断是否为空
        if(nodeArray == null || nodeArray.length == 0){
            throw new RuntimeException("RedisSentinelConfiguration initialize error nodeArray is null");
        }
        //循环注入至Set中
        for(String node : nodeArray){
            System.out.println("Read node : "+node);
            nodeSet.add(node);
        }
        //创建连接池对象
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(master,nodeSet,jedisPoolConfig ,timeout);
        return jedisSentinelPool;
    }

}
