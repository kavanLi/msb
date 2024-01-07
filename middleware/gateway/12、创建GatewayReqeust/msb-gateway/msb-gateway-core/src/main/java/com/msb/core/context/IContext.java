package com.msb.core.context;

import com.msb.common.rule.Rule;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 核心上下文的接口定义
 */
public interface IContext {
    //声明生命周期状态

    /**
     * 一个请求正在执行的状态
     */
    int RUNNING = 0;
    /**
     * 标志请求结束，写回Reponse，返回结果
     */
    int WRITTEN = 1;
    /**
     * 响应成功，设置该标识
     */
    int COMPLETED = 2;
    /**
     * 整个网关请求完毕，彻底结束
     */
    int TERMINATED = -1;


    /**
     * 设置上下文状态为正常运行状态
     */
    void running();

    /**
     * 设置上下文状态标记写回状态
     */
    void written();

    /**
     * 设置上下文状态为标记写回成功
     */
    void completed();

    /**
     * 标记网关结束
     */
    void terminated();

    /**
     * 判断网关的运行状态
     */
    boolean isRunning();
    boolean isWritten();
    boolean isCompleted();
    boolean isTerminated();


    /**
     * 获取请求协议
     */
    String getProtocol();

    /**
     * 获取请求规则
     */
    Rule getRule();

    /**
     * 获取请求对象
     */
    Object getReqeust();

    /**
     * 获取响应对象
     */
    Object getResponse();

    /**
     * 获取异常信息
     */
    Throwable getThrowble();

    /**
     * 获取上下文请求参数
     */
    Object getAttribute(Map<String,Object> key);


    /**
     * 设置请求规则
     */
    void setRule();
    /**
     * 设置请求返回结果
     */
    void setResponse();

    /**
     * 设置请求异常信息
     */
    void setThrowalbe(Throwable throwalbe);

    /**
     * 设置上下文参数
     */
    void setAttribute(String key ,Object obj);


    /**
     * 获取Netty上下文
     */
    ChannelHandlerContext getNettyCtx();

    /**
     * 是否保持长连接
     */
    boolean isKeepAlive();

    /**
     * 释放资源
     */
    void releaseRequest();
    /**
     * 设置回调函数
     */
    void setCompleted(Consumer<IContext> consumer);

    /**
     * 调用回调函数
     */
    void invokeCompletedCallBack();



}
