package com.bobo.mp.service;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import javax.sql.DataSource;

import com.bobo.mp.dataSource.annotation.ReportDB;
import com.bobo.mp.domain.pojo.DynaAmsUserOptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 需要在Application类或者测试类上增加注解
 *
 * @EnableAsync
 */
@Service
public class AsyncService {

    //@Autowired
    //private DynaAmsUserOptLogService dynaAmsUserOptLogService;
    //
    //@Autowired
    //private DynaAmsUserOptLogMapper dynaAmsUserOptLogMapper;

    @Autowired
    private DynaAmsOrganizationService dynaAmsOrganizationService;

    @Autowired
    ApplicationContext ctx;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 结束线程的方法：
     *
     * 1. 自然结束（能自然结束就尽量自然结束）
     * 2. stop() suspend() resume()
     * 3. volatile标志
     * 1. 不适合某些场景（比如还没有同步的时候，线程做了阻塞操作，没有办法循环回去）
     * 2. 打断时间也不是特别精确，比如一个阻塞容器，容量为5的时候结束生产者，
     * 但是，由于volatile同步线程标志位的时间控制不是很精确，有可能生产者还继续生产一段儿时间
     * 4. interrupt() and isInterrupted（比较优雅）
     */
    private static volatile Boolean running = true;

    /**
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
    public void test1() {
        taskExecutor.execute(() -> test());
    }

    // 相比@Async注解方式,更加灵活和可控,适用于需要细粒度执行控制的场景
    //@Async("taskExecutorForHeavyTasks")
    @ReportDB
    public void test() {
        // 具体任务
        //DynaAmsUserOptLog dynaAmsUserOptLog1 = dynaAmsUserOptLogService.getById(360930L);

        //DynaAmsUserOptLog dynaAmsUserOptLog = DynaAmsUserOptLog.builder()
        //        .createSource(3L)
        //        .loginName("createUser" + "/" + "updateUser")
        //        .ipAddress("reqip" + "/" + "request.getRemoteAddr()")
        //        .requestUrl("request.getRequestURI()")
        //        .optName("optName")
        //        .requestTime(new Date())
        //        .requestParam("String.valueOf(args)").build();
        //Boolean save1 = dynaAmsUserOptLogMapper.insertDynaAmsUserOptLog(dynaAmsUserOptLog);
        //boolean save = dynaAmsUserOptLogService.save(dynaAmsUserOptLog);
        //while (running) {
        //    System.out.println(dynaAmsUserOptLog1.getOptName());
        //    try {
        //        Thread.sleep(1000);
        //    } catch (InterruptedException e) {
        //        throw new RuntimeException(e);
        //    }
        //}
    }

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