package com.msb.core.cycle;

/**
 * 框架里面有很多组件有生命周期，比方上下文，或者后面我们讲的netty服务
 * 并且这些组件生命周期都有共同的方法
 */
public interface LifeCycle {
    /**
     * 初始化
     */
    void init();

    /**
     * 启动
     */
    void start();

    /**
     * 关闭
     */
    void shutdown();
}

