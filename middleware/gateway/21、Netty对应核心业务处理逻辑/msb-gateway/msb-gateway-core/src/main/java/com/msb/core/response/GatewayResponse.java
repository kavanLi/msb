package com.msb.core.response;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.msb.common.enums.ResponseCode;
import com.msb.common.util.JSONUtil;
import io.netty.handler.codec.http.*;
import lombok.Data;
import org.asynchttpclient.Response;

@Data
public class GatewayResponse {

    // 响应头
    private HttpHeaders responseHeaders = new DefaultHttpHeaders();
    // 额外的响应结果
    private final HttpHeaders extraResponseHeaders = new DefaultHttpHeaders();
    // 返回响应的内容
    private String content;
    // 返回响应的状态码
    private HttpResponseStatus httpResponseStatus;

    // 如果是异步的话，还需要一个异步返回的对象
    private Response futureResponse;

    public GatewayResponse(){}

    /**
     * 设置响应头信息
     */
    public void putHeader(CharSequence key, CharSequence val){
        responseHeaders.add(key,val);
    }

    /**
     * 构建网关响应对象
     * @param futureResponse
     * @return
     */
    public static GatewayResponse buildGatewayResponse( Response futureResponse){
        GatewayResponse response = new GatewayResponse();
        response.setFutureResponse(futureResponse);
        response.setHttpResponseStatus(HttpResponseStatus.valueOf(futureResponse.getStatusCode()));
        return  response;
    }

    /**
     * 返回一个json类型的响应信息，失败时候使用
     * @param code
     * @param args
     * @return
     */
    public static GatewayResponse buildGatewayResponse(ResponseCode code ,Object... args){
        ObjectNode objectNode = JSONUtil.createObjectNode();
        objectNode.put(JSONUtil.STATUS,code.getStatus().code());
        objectNode.put(JSONUtil.CODE,code.getCode());
        objectNode.put(JSONUtil.MESSAGE,code.getMessage());
        GatewayResponse response = new GatewayResponse();
        response.setHttpResponseStatus(code.getStatus());
        response.putHeader(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON+ "; charset=utf-8");
        response.setContent(JSONUtil.toJSONString(objectNode));
        return response;
    }

    /**
     * 返回一个json类型的响应信息，成功的时候
      * @param data
     * @return
     */
    public static GatewayResponse buildGatewayResponse(Object data){
        ObjectNode objectNode = JSONUtil.createObjectNode();
        objectNode.put(JSONUtil.STATUS,ResponseCode.SUCCESS.getStatus().code());
        objectNode.put(JSONUtil.CODE,ResponseCode.SUCCESS.getCode());
        objectNode.putPOJO(JSONUtil.DATA,data);
        GatewayResponse response = new GatewayResponse();
        response.setHttpResponseStatus(ResponseCode.SUCCESS.getStatus());
        response.putHeader(HttpHeaderNames.CONTENT_TYPE,
                HttpHeaderValues.APPLICATION_JSON + ";charset=utf-8");
        response.setContent(JSONUtil.toJSONString(objectNode));
        return response;
    }

}
