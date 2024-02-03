package com.msb.core.config;

import lombok.Data;

@Data
public class Config {
    private int port = 8888;

    private String applicationName = "msb-gateway";

    private String registryAddress = "127.0.0.1:8848";

    private String env = "dev";

    // Netty相关内容
    // boss线程数
    private int eventLoopGroupBossNum = 1;
    // work线程数
    private int eventLoopGroupWorkNum = Runtime.getRuntime().availableProcessors();
    // 内容大小 64M
    private int maxContentLength = 64 * 1024 *1024;
    // 默认单异步模式
    private boolean whenComplete = true;

    // Http Async 参数选项
    // 连接超时时间
    private int httpConnectTimeout = 30 * 1000;

    // 请求超时时间
    private int httpRequestTimeout = 30 *1000;

    //客户端请求重试次数
    private int httpMaxRequestRetry = 2;

    // 客户端请求最大连接数
    private int httpMaxConnections = 10000;

    // 客户端每个地址支持的最大链接数
    private int httpConnectionsPerHost = 8000;

    // 客户端空闲链接超时时间，默认60s
    private int httpPooledConnectionIdleTimeout = 60 * 1000;


}














