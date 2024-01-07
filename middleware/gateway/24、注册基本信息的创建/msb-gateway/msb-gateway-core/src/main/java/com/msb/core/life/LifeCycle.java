package com.msb.core.life;
// 框架里面有很多，组件都是有周期，比如上下文，和 后面我们讲的Netty，并且这些组件的生命周期
//都有一些共同的方法
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
