package com.msb.core.netty;

import com.msb.common.util.RemotingUtil;
import com.msb.core.config.Config;
import com.msb.core.cycle.LifeCycle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;

import java.net.InetSocketAddress;

@Slf4j
public class NettyHttpServer implements LifeCycle {

    // 引入启动的配置类
    private final Config config;
    // 启动类
    private ServerBootstrap serverBootstrap;
    // boss线程组
    private EventLoopGroup eventLoopGroupBoss;
    // work线程组
    private EventLoopGroup eventLoopGroupWork;

    public NettyHttpServer(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        this.serverBootstrap = new ServerBootstrap();
        if(userEpoll()){
            this.eventLoopGroupBoss = new EpollEventLoopGroup(config.getEventLoopGroupBossNum(),
                    new DefaultThreadFactory("netty-boss-nio"));
            this.eventLoopGroupWork = new EpollEventLoopGroup(config.getEventLoopGroupWorkNum(),
                    new DefaultThreadFactory("netty-work-nio"));
        }else{
            this.eventLoopGroupBoss = new NioEventLoopGroup(config.getEventLoopGroupBossNum(),
                    new DefaultThreadFactory("netty-boss-nio"));
            this.eventLoopGroupWork = new NioEventLoopGroup(config.getEventLoopGroupWorkNum(),
                    new DefaultThreadFactory("netty-work-nio"));
        }

    }

    private boolean userEpoll() {
        return RemotingUtil.isLinuxPlatform() && Epoll.isAvailable();
    }

    @Override
    public void start() {
        this.serverBootstrap
                .group(eventLoopGroupBoss,eventLoopGroupWork)
                .channel(userEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(config.getPort()))
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        channel.pipeline().addLast(
                                new HttpServerCodec(),// http编解码
                                new HttpObjectAggregator(config.getMaxContentLength()),//请求报文聚合成FullHttpRequest
                                new NettyServerConnectManagerHandler(),
                                new NettyHttpServerHandler()
                        );
                    }
                });

        try {
            this.serverBootstrap.bind().sync();
            log.info("server startup on port:{}" ,this.config.getPort());
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void shutdown() {
        // 优雅关机
        if(eventLoopGroupBoss != null){
            eventLoopGroupBoss.shutdownGracefully();
        }
        if(eventLoopGroupWork  != null){
            eventLoopGroupWork.shutdownGracefully();
        }

    }
}
