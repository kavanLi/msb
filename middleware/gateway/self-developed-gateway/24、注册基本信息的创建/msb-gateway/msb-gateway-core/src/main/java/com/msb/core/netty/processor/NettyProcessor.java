package com.msb.core.netty.processor;


import com.msb.core.context.HttpRequestWrapper;

public interface NettyProcessor {

    void process(HttpRequestWrapper wrapper);
}
