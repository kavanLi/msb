package com.msb.core.netty;

import com.msb.core.config.Config;
import com.msb.core.cycle.LifeCycle;
import com.msb.core.helper.AsyncHttpHelper;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.EventLoopGroup;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

import java.io.IOException;

// 对下游的请求转发
public class NettyHttpClient implements LifeCycle {
    private final Config config;

    private final EventLoopGroup eventLoopGroupWork;

    private AsyncHttpClient asyncHttpClient;

    public NettyHttpClient(Config config, EventLoopGroup eventLoopGroupWork) {
        this.config = config;
        this.eventLoopGroupWork = eventLoopGroupWork;
        init();
    }

    @Override
    public void init() {
        DefaultAsyncHttpClientConfig.Builder builder = new DefaultAsyncHttpClientConfig.Builder();
        // 设置work线程组
        builder.setEventLoopGroup(eventLoopGroupWork)
                //设置连接超时
                .setConnectTimeout(config.getHttpConnectTimeout())
                // 设置请求超时
                .setRequestTimeout(config.getHttpRequestTimeout())
                //设置重试此时
                .setMaxRedirects(config.getHttpMaxRequestRetry())
                // 池化分配器
                .setAllocator(PooledByteBufAllocator.DEFAULT)
                // 压缩
                .setCompressionEnforced(true)
                // 最大链接数
                .setMaxConnections(config.getHttpMaxConnections())
                // 客户端米格地址支持的最大链接数
                .setMaxConnectionsPerHost(config.getHttpConnectionsPerHost())
                // 客户端空闲链接超时时间
                .setPooledConnectionIdleTimeout(config.getHttpPooledConnectionIdleTimeout());
        this.asyncHttpClient = new DefaultAsyncHttpClient(builder.build());
    }

    @Override
    public void start() {
        AsyncHttpHelper.getInstance().initialized(asyncHttpClient);
    }

    @Override
    public void shutdown() {
        if(asyncHttpClient != null){
            try {
                this.asyncHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
