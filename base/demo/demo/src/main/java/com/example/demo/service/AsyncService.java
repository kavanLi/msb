package com.example.demo.service;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 需要在Application类或者测试类上增加注解
 * @EnableAsync
 */
@Service
@Slf4j
public class AsyncService {

    @Async
    public void processUpdateAndLog(String updatedParameters) {
        try {
            System.out.println("開始休息");
            Thread.sleep(3000);
            System.out.println("休息結束");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 异步执行数据库查询和日志插入操作
        // 查询数据库获取更新参数
        // 插入日志表
    }

    @Async
    public CompletableFuture <Void> processCompletableFuture(String updatedParameters) {
        try {
            log.info("開始休息");
            System.out.println("開始休息");
            Thread.sleep(3000);
            System.out.println("休息結束");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 异步执行数据库查询和日志插入操作
        // 查询数据库获取更新参数
        // 插入日志表

        // 返回一个已经完成的 CompletableFuture，表示异步方法执行完成
        return CompletableFuture.completedFuture(null);
    }
}