package com.msb.core.context;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

@Data
public class HttpReqeustWrapper {
    private FullHttpRequest request;
    private ChannelHandlerContext ctx;
}
