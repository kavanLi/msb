package com.mashibing.smsgateway.netty4;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NettyStartCMPP {

    //ip
    public static String host = "127.0.0.1";
    //端口
    public static int port = 7890;
    //账号
    public static String serviceId = "laozheng";
    //密码
    public static String pwd = "JavaLaoZheng123!";



    @Bean(initMethod = "start")
    public NettyClient nettyClient() {
        NettyClient cmppClient = new NettyClient(host, port, serviceId, pwd);
        return cmppClient;
    }
}
