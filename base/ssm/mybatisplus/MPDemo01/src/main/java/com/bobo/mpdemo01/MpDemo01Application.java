package com.bobo.mpdemo01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *    配置MyBatis的Mapper接口路径
 */

@SpringBootApplication
public class MpDemo01Application {

    public static void main(String[] args) {
        SpringApplication.run(MpDemo01Application.class, args);
    }

}
