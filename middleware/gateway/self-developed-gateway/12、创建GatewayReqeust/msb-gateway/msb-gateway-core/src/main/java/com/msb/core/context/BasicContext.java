package com.msb.core.context;

import com.msb.common.rule.Rule;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * 核心上下文的基础类
 */
public class BasicContext implements IContext{

    /**
     * 转发协议
     */
    protected final String protocol;

    /**
     * Netty上下文
     */
    protected final ChannelHandlerContext nettyCtx;

    /**
     * 是否保持长连接
     */
    protected final boolean keepAlive;

    /**
     * 上下文状态
     */
    private volatile int status = IContext.RUNNING;

    /**
     * 上下文参数集合
     */
    private final Map<String,Object> attributes = new HashMap<>();

    /**
     * 请求过程发生异常
     */
    private Throwable throwable;

    /**
     * 是否已经释放资源
     */
    private final AtomicBoolean requestReleased = new AtomicBoolean(false);

    /**
     * 存放回调函数的集合
     */
    private List<Consumer<IContext>> completedCallbacks;


    public BasicContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive) {
        this.protocol = protocol;
        this.nettyCtx = nettyCtx;
        this.keepAlive = keepAlive;
    }

    @Override
    public void running() {
        status = IContext.RUNNING;
    }

    @Override
    public void written() {
        status = IContext.WRITTEN;
    }

    @Override
    public void completed() {
        status = IContext.COMPLETED;
    }

    @Override
    public void terminated() {
        status = IContext.TERMINATED;
    }

    @Override
    public boolean isRunning() {
        return status == IContext.RUNNING;
    }

    @Override
    public boolean isWritten() {
        return status == IContext.WRITTEN;
    }

    @Override
    public boolean isCompleted() {
        return status == IContext.COMPLETED;
    }

    @Override
    public boolean isTerminated() {
        return status == IContext.TERMINATED;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public Rule getRule() {
        return null;
    }

    @Override
    public Object getReqeust() {
        return null;
    }

    @Override
    public Object getResponse() {
        return null;
    }

    @Override
    public Throwable getThrowble() {
        return this.throwable;
    }

    @Override
    public Object getAttribute(Map<String, Object> key) {
        return this.attributes.get(key);
    }

    @Override
    public void setRule() {

    }

    @Override
    public void setResponse() {

    }

    @Override
    public void setThrowalbe(Throwable throwalbe) {
        this.throwable = throwalbe;
    }

    @Override
    public void setAttribute(String key, Object obj) {
        this.attributes.put(key,obj);
    }

    @Override
    public ChannelHandlerContext getNettyCtx() {
        return this.nettyCtx;
    }

    @Override
    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    @Override
    public void releaseRequest() {
        this.requestReleased.compareAndSet(false,true);
    }

    @Override
    public void setCompleted(Consumer<IContext> consumer) {
        if(completedCallbacks == null){
            completedCallbacks = new ArrayList<>();
        }
        completedCallbacks.add(consumer);
    }

    @Override
    public void invokeCompletedCallBack() {
        if(completedCallbacks != null){
            completedCallbacks.forEach(consume ->consume.accept(this));
        }
    }

}
