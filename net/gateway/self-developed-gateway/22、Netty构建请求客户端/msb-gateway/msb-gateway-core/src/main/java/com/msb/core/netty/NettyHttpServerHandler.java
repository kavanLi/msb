package com.msb.core.netty;

import com.msb.core.context.HttpReqeustWrapper;
import com.msb.core.netty.processor.NettyProcessor;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

public class NettyHttpServerHandler extends SimpleChannelInboundHandler {
    private final NettyProcessor nettyProcessor;

    public NettyHttpServerHandler(NettyProcessor nettyProcessor) {
        this.nettyProcessor = nettyProcessor;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;

        // 创建一个包装器
        HttpReqeustWrapper httpReqeustWrapper = new HttpReqeustWrapper();
        httpReqeustWrapper.setCtx(ctx);
        httpReqeustWrapper.setRequest(request);

        nettyProcessor.process(httpReqeustWrapper);

    }
}
