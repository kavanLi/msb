package com.msb.core.context;

import com.msb.common.config.Rule;
import com.msb.core.request.GatewayRequest;
import com.msb.core.response.GatewayResponse;
import io.netty.channel.ChannelHandlerContext;

public class GatewayContext extends BasicContext {

    /**
     * 请求
     */
    private GatewayRequest request;
    /**
     * 响应
     */
    private GatewayResponse response;

    /**
     * 规则
     */
    private Rule rule;

    /**
     * 构造函数
     *
     * @param protocol
     * @param nettyCtx
     * @param keepAlive
     */
    public GatewayContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive,
                          GatewayRequest request, Rule rule) {
        super(protocol, nettyCtx, keepAlive);
        this.request = request;
        this.rule = rule;
    }

    @Override
    public GatewayRequest getRequest() {
        return request;
    }

    public void setRequest(GatewayRequest request) {
        this.request = request;
    }

    @Override
    public GatewayResponse getResponse() {
        return response;
    }

    public void setResponse(GatewayResponse response) {
        this.response = response;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getUniqueId(){
        return request.getUniquedId();
    }
}
