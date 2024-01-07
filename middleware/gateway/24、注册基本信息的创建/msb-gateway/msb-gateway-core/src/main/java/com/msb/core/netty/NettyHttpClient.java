package com.msb.core.netty;

import com.msb.core.life.LifeCycle;
import com.msb.core.config.Config;
import com.msb.core.helper.AsyncHttpHelper;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import java.io.IOException;

// 对下游的请求转发
@Slf4j
public class NettyHttpClient implements LifeCycle {
    private final Config config;

    private final EventLoopGroup eventLoopGroupWoker;

    private AsyncHttpClient asyncHttpClient;

    public NettyHttpClient(Config config, EventLoopGroup eventLoopGroupWoker) {
        this.config = config;
        this.eventLoopGroupWoker = eventLoopGroupWoker;
        init();
    }

    @Override
    public void init() {
        DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder()
                // 设置work线程组
                .setEventLoopGroup(eventLoopGroupWoker)
                // 设置连接超时
                .setConnectTimeout(config.getHttpConnectTimeout())
                // 设置请求超时
                .setRequestTimeout(config.getHttpRequestTimeout())
                // 设置重试次数
                .setMaxRedirects(config.getHttpMaxRequestRetry())
                // 池化的分配器
                .setAllocator(PooledByteBufAllocator.DEFAULT) //池化的byteBuf分配器，提升性能
                // 压缩
                .setCompressionEnforced(true)
                // 最大链接数
                .setMaxConnections(config.getHttpMaxConnections())
                // 客户端每个地址支持的最大连接数
                .setMaxConnectionsPerHost(config.getHttpConnectionsPerHost())
                // 	客户端空闲连接超时时间, 默认60秒
                .setPooledConnectionIdleTimeout(config.getHttpPooledConnectionIdleTimeout());
        this.asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
    }

    @Override
    public void start() {
        AsyncHttpHelper.getInstance().initialized(asyncHttpClient);
    }

    @Override
    public void shutdown() {
        if (asyncHttpClient != null) {
            try {
                this.asyncHttpClient.close();
            } catch (IOException e) {
                log.error("NettyHttpClient shutdown error", e);
            }
        }
    }
}
