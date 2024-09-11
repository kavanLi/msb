package com.msb.es.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "ql.es",ignoreInvalidFields=true)
public class QLElasticsearchValue {
    private String esUrl;   //url
    private boolean connectEnabled;
    private String username;
    private String password;
    private boolean userEnabled;
    private int connectTimeOut = 1000; // 连接超时时间
    private int socketTimeOut = 30000; // 连接超时时间
    private int connectionRequestTimeOut = 500; // 获取连接的超时时间
    private int maxConnectNum = 100; // 最大连接数
    private int maxConnectPerRoute = 100; // 最大路由连接数

}