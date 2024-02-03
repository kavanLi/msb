package com.msb.core.request;

import org.asynchttpclient.Request;
import org.asynchttpclient.cookie.Cookie;

/**
 * 网关请求接口
 */
public interface IGatewayRequest {

    /**
     * 添加请求头信息
     * @param name
     * @param value
     */
    void addHeader(CharSequence name,String value);

    /**
     * 设置请求头信息
     * @param name
     * @param value
     */
    void setHeader(CharSequence name,String value);

    /**
     * 添加或者删除cookie
     * @param cookie
     */
    void addOrReplaceCookie(Cookie cookie);

    /**
     * 设置超时时间
     * @param requestTimeout
     */
    void setRequestTimeout(int requestTimeout);

    /**
     * 修改域名
     * @param host
     */
    void setModifyHost(String host);

    /**
     * 获取域名
     * @param host
     */
    String getModifyHost();

    /**
     * 设置/获取路径
     * @param path
     */
    void setModifyPath(String path);
    String getModifyPath();

    /**
     * Get 增加请求参数
     * @param name
     * @param value
     */
    void addQueryParam(String name, String value);

    /**
     * POST 增加请求参数
     * @param name
     * @param value
     */
    void addFormParam(String name,String value);

    /**
     * 获取最终的请求路径
     * @return
     */
    String getFinalUrl();

    /**
     * 构建最终的请求对象
     * @return
     */
    Request build();
}






























