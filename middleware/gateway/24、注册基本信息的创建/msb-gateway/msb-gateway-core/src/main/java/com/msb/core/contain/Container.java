package com.msb.core.contain;

import com.msb.core.life.LifeCycle;
import com.msb.core.config.Config;
import com.msb.core.netty.NettyHttpClient;
import com.msb.core.netty.NettyHttpServer;
import com.msb.core.netty.processor.NettyCoreProcessor;
import com.msb.core.netty.processor.NettyProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Container implements LifeCycle {
    private final Config config;

    private NettyHttpServer nettyHttpServer;

    private NettyHttpClient nettyHttpClient;

    private NettyProcessor nettyProcessor;

    public Container(Config config) {
        this.config = config;
        init();
    }

    @Override
    public void init() {
        this.nettyProcessor = new NettyCoreProcessor();

        this.nettyHttpServer = new NettyHttpServer(config, nettyProcessor);

        this.nettyHttpClient = new NettyHttpClient(config,
                nettyHttpServer.getEventLoopGroupWoker());
    }

    @Override
    public void start() {
        nettyHttpServer.start();;
        nettyHttpClient.start();
        log.info("api gateway started!");
    }

    @Override
    public void shutdown() {
        nettyHttpServer.shutdown();
        nettyHttpClient.shutdown();
    }
}
