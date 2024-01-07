package com.msb.core.context;

import com.msb.common.config.Rule;
import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 核心上下文接口定义
 */
public interface IContext {
    // part1 声明 一些状态常量

    /**
     * 一个请求正在执行的状态
     */
    int RUNNING = 0;

    /**
     * 标志请求结束，写回Response
     */
    int WRITTEN = 1;

    /**
     * 响应成功，设置该标识，例如： Netty ctx.WriteAndFlush(Response);
     */
    int  COMPLETED = 2;

    /**
     * 整个网关请求完毕，彻底结束
     */
    int TERMINATED = -1;

    // part2  设置
    /**
     * 设置上下文状态为正常运行状态
     */
    void running();

    /**
     * 设置上下文状态为标记写回状态
     */
    void written();

    /**
     * 设置上下文状态为标记写回成功
     */
    void completed();

    /**
     * 标志网关结束
     */
    void terminated();

    // part3  判断

    /**
     * 判断网关状态运行状态
     * @return
     */
    boolean isRunning();
    boolean isWritten();
    boolean isCompleted();
    boolean isTerminated();


    // part4 获取 公共方法

    /**
     * 获取请求转换协议
     * @return
     */
    String getProtocol();

    /**
     * 获取请求规则
     * @return
     */
    Rule getRule();

    /**
     * 获取请求对象
     * @return
     */
    Object getRequest();

    /**
     * 获取请求响应
     * @return
     */
    Object getResponse();

    /**
     * 获取异常信息
     * @return
     */
    Throwable getThrowble();

    /**
     * 获取上下文请求参数
     * @param key
     * @return
     */
    Object getAttribute(Map<String,Object> key);

    // part4 设置 公共方法

    /**
     * 设置请求规则
     * @return
     */
    void setRule();
    /**
     * 设置请求返回结果
     * @return
     */
    void setResponse();
    /**
     * 设置请求异常信息
     * @return
     */
    void setThrowable(Throwable throwable);
    /**
     * 设置上下文参数
     * @return
     */
    void setAttribute(String key,Object obj);


    // Part 5 else

    /**
     * 获取Netty上下文
     * @return
     */
    ChannelHandlerContext getNettyCtx();

    /**
     * 是否保持连接
     * @return
     */
    boolean isKeepAlive();

    /**
     * 释放资源
     */
    void releaseRequest();

    /**
     * 设置回调函数
     */
    void setCompletedCallBack(Consumer<IContext> consumer);

    /**
     * 调用回调函数
     */
    void invokeCompletedCallBack( );

}
