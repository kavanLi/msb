package com.msb.core.request;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.msb.common.constants.BasicConst;
import com.msb.core.context.BasicContext;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;
import org.asynchttpclient.cookie.Cookie;

import java.nio.charset.Charset;
import java.util.*;

/**
 * 网络请求类
 */
@Slf4j
public class GatewayRequest implements IGatewayRequest{

    /**
     * 服务ID
     */
    @Getter
    private final String uniquedId;

    /**
     * 请求进入网关时间
     */
    @Getter
    private final long beginTime;

    /**
     * 字符集不会变
     */
    @Getter
    private final Charset charset;

    /**
     * 客户端IP，主要用于过留空，黑白名单
     */
    @Getter
    private final String clientIp;

    /**
     * 请求地址： ip:port
     */
    @Getter
    private final String host;

    /**
     * 请求路径 /xxx/xxxx
     */
    @Getter
    private final String path;

    /**
     * URL：统一资源定位符
     */
    @Getter
    private final String uri;

    /**
     * 请求方式 POST/GET
     */
    @Getter
    private final HttpMethod method;

    /**
     * 请求格式
     */
    @Getter
    private final String contentType;

    /**
     * 请求头信息
     */
    @Getter
    private final HttpHeaders headers;

    /**
     * 参数解析器
     */
    @Getter
    private final QueryStringDecoder queryStringDecoder;
    /**
     * 该对象便可获取到所有与http协议有关的信息
     */
    @Getter
    private final FullHttpRequest fullHttpRequest;
    /**
     * 请求体
     */
    @Getter
    private String body;

    /**
     * 请求cookie
     */
    private Map<String, io.netty.handler.codec.http.cookie.Cookie> cookieMap;

    /**
     * post请求定义的参数
     */
    private Map<String, List<String>>  postParameeters;

    /*********可修改的请求变量**********/
    /**
     * 可以修改Scheme 默认是http://
     */
    private String modifyScheme;

    private String modifyHost;

    private String modifyPath;

    /**
     * 构建下游请求的http请求构建起
     */
    private final RequestBuilder requestBuilder;

    public GatewayRequest(String uniquedId,  Charset charset, String clientIp, String host, String uri,
                          HttpMethod method, String contentType, HttpHeaders headers,
                          FullHttpRequest fullHttpRequest) {
        this.uniquedId = uniquedId;
        this.beginTime = System.currentTimeMillis();
        this.charset = charset;
        this.clientIp = clientIp;
        this.host = host;

        this.uri = uri;
        this.method = method;
        this.contentType = contentType;
        this.headers = headers;
        this.queryStringDecoder = new QueryStringDecoder(uri,charset);
        this.path = queryStringDecoder.path();
        this.fullHttpRequest = fullHttpRequest;
        this.modifyHost = host;
        this.modifyPath = path;

        this.modifyScheme = BasicConst.HTTP_PREFIX_SEPARATOR;

        this.requestBuilder = new RequestBuilder();
        this.requestBuilder.setMethod(getMethod().name());
        this.requestBuilder.setHeaders(getHeaders());
        this.requestBuilder.setQueryParams(queryStringDecoder.parameters());

        ByteBuf contentBuffer = fullHttpRequest.content();
        if(Objects.nonNull(contentBuffer)){
            this.requestBuilder.setBody(contentBuffer.nioBuffer());
        }
    }

    /**
     * 获取请求体
     * @return
     */
    public String getBody(){
        if(StringUtils.isEmpty(body)){
            body = fullHttpRequest.content().toString(charset);
        }
        return body;
    }

    /**
     * 获取Cookie
     * 将HTTP请求中的cookie转化为Netty类型中的cookie
     * @param name
     * @return
     */
    public io.netty.handler.codec.http.cookie.Cookie getCookie(String name){
        if(cookieMap == null){
            cookieMap = new HashMap<String, io.netty.handler.codec.http.cookie.Cookie>();
            String cookieStr = getHeaders().get(HttpHeaderNames.COOKIE);
            Set<io.netty.handler.codec.http.cookie.Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieStr);
            for (io.netty.handler.codec.http.cookie.Cookie cookie : cookies) {
                cookieMap.put(name,cookie);
            }
        }
        return cookieMap.get(name);
    }

    /**
     * Get 请求获取指定淡出名称的值
     * @param name
     * @return
     */
    public List<String>  getQueryParametersMultiple(String name){
        return queryStringDecoder.parameters().get(name);
    }

    /**
     * Post 请求获取指定名称参数值
     * @param name
     * @return
     */
    public List<String> getPostParametersMultiples(String name){
        String body = getBody();
        if(isFormPost()){
            if(postParameeters == null){
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(body, false);
                postParameeters = queryStringDecoder.parameters();
            }
            if(postParameeters == null || postParameeters.isEmpty()){
                return null;
            }else{
                return postParameeters.get(name);
            }
        }else if(isJsonPost()){
            return Lists.newArrayList(JsonPath.read(body,name).toString());
        }
        return null;
    }




    @Override
    public void addHeader(CharSequence name, String value) {
        requestBuilder.addHeader(name,value);
    }

    @Override
    public void setHeader(CharSequence name, String value) {
        requestBuilder.setHeader(name,value);
    }

    @Override
    public void addOrReplaceCookie(Cookie cookie) {
        requestBuilder.addOrReplaceCookie(cookie);
    }

    @Override
    public void setRequestTimeout(int requestTimeout) {
        requestBuilder.setRequestTimeout(requestTimeout);
    }

    @Override
    public void setModifyHost(String modifyHost) {
        this.modifyHost = modifyHost;
    }

    @Override
    public String getModifyHost() {
        return modifyHost;
    }

    @Override
    public void setModifyPath(String modifyPath) {
        this.modifyPath = modifyPath;
    }

    @Override
    public String getModifyPath() {
        return this.modifyPath;
    }

    @Override
    public void addQueryParam(String name, String value) {
        requestBuilder.addQueryParam(name,value);
    }

    @Override
    public void addFormParam(String name, String value) {
        if(isFormPost()){
            requestBuilder.addFormParam(name,value);
        }
    }

    @Override
    public String getFinalUrl() {
        return modifyScheme + modifyHost + modifyPath;
    }

    @Override
    public Request build() {
        requestBuilder.setUrl(getFinalUrl());
        return requestBuilder.build();
    }

    public  boolean isFormPost(){
        return HttpMethod.POST.equals(method) &&
                (contentType.startsWith(HttpHeaderValues.FORM_DATA.toString()) ||
                        contentType.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()));
    }

    public  boolean isJsonPost(){
        return HttpMethod.POST.equals(method) && contentType.startsWith(HttpHeaderValues.APPLICATION_JSON.toString());
    }




}
