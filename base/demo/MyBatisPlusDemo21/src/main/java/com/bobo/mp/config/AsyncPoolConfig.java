package com.bobo.mp.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步线程池
 * AsyncConfigurer是给@Async注解使用的
 * 使用@Async或实现AsyncConfigurer配置自定义线程池主要有以下几点需要注意的地方:
 *
 * @Async方法需要try/catch处理异常,否则异常不能被调用方感知,可能导致难以追踪的bug。
 * @Async使用需要考虑方法是无状态的,不依赖于外部状态变更。
 * @Async方法返回值为Future时,需要处理取消、超时等情况。
 * 通过AsyncConfigurer配置会作用于所有@Async注解方法,不够灵活。
 * 直接通过ThreadPoolTaskExecutor提交任务可以精细控制任务提交,但没有@Async简单。
 * 因此推荐的使用方式结合考虑:
 *
 * 默认情况下使用@Async注解,简单易用,满足大部分需求。
 * 在@Async方法中try/catch处理异常,避免隐式异常。
 * 对于需要精细控制的任务提交,直接通过ThreadPoolTaskExecutor提交。
 * 尽量使@Async方法无状态化,不依赖外部变更。
 * 通过AsyncConfigurer配置默认线程池作为全局缺省配置。
 * 根据不同任务类型配置不同的ThreadPoolTaskExecutor。
 * 综合运用这些方式,可以发挥它们各自的优势,实现灵活可控的异步执行。
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncPoolConfig implements AsyncConfigurer {


    /**
     * 实现AsyncConfigurer接口
     * 还可以全局指定@Async使用的线程池:
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        return taskExecutorDefault();
    }
    //@Bean
    //public TaskExecutor asyncExecutor() {
    //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    //    // 设置核心线程数
    //    executor.setCorePoolSize(25);
    //    // 设置最大线程数
    //    executor.setMaxPoolSize(50);
    //    // 设置队列容量
    //    executor.setQueueCapacity(200);
    //    // 设置线程活跃时间（秒）
    //    executor.setKeepAliveSeconds(60);
    //    // 设置默认线程名称
    //    executor.setThreadNamePrefix("Async-Executor-");
    //    // 设置拒绝策略
    //    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    //    // 等待所有任务结束后再关闭线程池
    //    executor.setWaitForTasksToCompleteOnShutdown(true);
    //    return executor;
    //}

    @Primary
    @Bean(name = "taskExecutorDefault")
    public ThreadPoolTaskExecutor taskExecutorDefault() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        String threadName = "Async-1-";
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix(threadName);
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler((r, executor1) -> log.warn(threadName + ": Task rejected, thread pool is" +
                " " +
                "full and queue is also full"));
        executor.initialize();
        return executor;
    }

    @Bean(name = "taskExecutorForHeavyTasks")
    public ThreadPoolTaskExecutor taskExecutorRegistration() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        String threadName = "Async2-";
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Async2-");
        executor.setKeepAliveSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler((r, executor1) -> log.warn(threadName + ": Task rejected, thread pool is" +
                " " +
                "full and queue is also full"));
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
