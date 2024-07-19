package com.msb.caffeine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


/**
 * 启动类
 *    配置MyBatis的Mapper接口路径
 */

@SpringBootApplication
@EnableCaching
public class MpDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(MpDemo01Application.class, args);
    }

}
