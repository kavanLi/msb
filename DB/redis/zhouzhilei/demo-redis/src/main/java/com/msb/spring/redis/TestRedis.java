package com.msb.spring.redis;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-17 00:16
 * To change this template use File | Settings | File and Code Templates.
 */
@Component
@Slf4j
public class TestRedis {
    /* fields -------------------------------------------------------------- */
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("ooxx")
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */
    public void testRedis() {
        //redisTemplate.opsForValue().set("name", "kavanLi-R7000");
        //stringRedisTemplate.opsForValue().set("name1", "kavan7");

        RedisConnection connection = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection();
        connection.set("name2131".getBytes(), "kavanLi-R7000232323".getBytes());
        stringRedisTemplate.opsForValue().set("name1", "kavan7");

        //log.info(Objects.requireNonNull(redisTemplate.opsForValue().get("name2131")).toString());
        log.info(new String(Objects.requireNonNull(connection.get("name2131".getBytes()))));
        log.info(Objects.requireNonNull(stringRedisTemplate.opsForValue().get("name1")));

        connection.close();

        //HashOperations <String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();
        //stringObjectObjectHashOperations.put("sean", "name", "zhouzhilei");
        //stringObjectObjectHashOperations.put("sean", "age", 22);
        //log.info(String.valueOf(Objects.requireNonNull(stringObjectObjectHashOperations.entries("sean"))));



        Person person = new Person();
        person.setAge(233);
        person.setName("zhouzhilei111111");


        //stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer <Object>(Object.class));

        Jackson2HashMapper jackson2HashMapper = new Jackson2HashMapper(objectMapper, false);

        stringRedisTemplate.opsForHash().putAll("sean01", jackson2HashMapper.toHash(person));
        Map sean01 = stringRedisTemplate.opsForHash().entries("sean01");
        System.out.println(objectMapper.convertValue(sean01, Person.class));



    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
