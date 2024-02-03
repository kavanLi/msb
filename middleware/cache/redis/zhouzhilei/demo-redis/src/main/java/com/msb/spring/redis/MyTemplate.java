package com.msb.spring.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author: kavanLi-R7000
 * @create: 2023-09-17 12:34
 * To change this template use File | Settings | File and Code Templates.
 */
@Configuration
public class MyTemplate {
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    @Bean
    public StringRedisTemplate ooxx(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer <Object>(Object.class));
        return stringRedisTemplate;
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
