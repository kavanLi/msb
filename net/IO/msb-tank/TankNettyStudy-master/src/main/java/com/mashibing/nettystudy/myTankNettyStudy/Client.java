package com.mashibing.nettystudy.myTankNettyStudy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Client {
    public static void main(String[] args) {
        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();

        try {


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
