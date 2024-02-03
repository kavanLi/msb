package com.msb.core.netty.processor;

import com.msb.core.context.HttpReqeustWrapper;

public interface NettyProcessor {
    void process(HttpReqeustWrapper httpReqeustWrapper);
}
