package com.msb.core.request;

import org.asynchttpclient.Request;
import org.asynchttpclient.cookie.Cookie;

/**
 * 网络请求的Request接口
 */
public interface IGatewayRequest {

    /**
     * 添加我请求头信息
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
     * 添加或者替换Cookie
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
     * @param modifyHost
     */
    void setModifyHost(String modifyHost);

    /**
     * 设置路径
     * @param modifyPath
     */
    void setModifyPath(String modifyPath);

    /**
     * 获取路径
     */
    String getModifyPath();

    /**
     * Get请求参数
     * @param name
     * @param value
     */
    void addQueryParam(String name,String value);

    /**
     * POST增加参数
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
