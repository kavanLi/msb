package com.example.demo.doc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.SneakyThrows;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-26 16:23
 * To change this template use File | Settings | File and Code Templates.
 */
public class AlibabaDevManual {


    public static void main(String[] args) {

        /**
         * (七) 并发处理
         */
        //concurrencyTest12();
    }


    /**
     * (七) 并发处理
     */

    /**
     * 12.【强制】 多线程并行处理定时任务时， Timer 运行多个 TimeTask 时， 只要其中之一没有捕获抛出的异
     * 常， 其它任务便会自动终止运行， 使用 ScheduledExecutorService 则没有这个问题。
     *
     * Timer 使用单线程，所有任务都在同一个线程中执行。如果一个任务抛出异常，会导致线程终止，进而终止所有任务的执行。
     * ScheduledExecutorService 使用线程池，每个任务都在独立的线程中执行。如果一个任务抛出异常，只会影响当前线程，不会影响其他任务的执行。
     */
    public static void concurrencyTest12() {

        // Timer
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                throw new RuntimeException("Timer Error!");
            }
        }, 100, 1000);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("This Timer task will not be executed!");
            }
        }, 100, 1000);

        // ScheduledExecutorService
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(123);
                throw new RuntimeException("ScheduledExecutorService Error!");
            }
        }, 100, 1000, TimeUnit.MILLISECONDS);

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("This ScheduledExecutorService task will be executed!");
            }
        }, 100, 1000, TimeUnit.MILLISECONDS);

    }

    /**
     * (十) 前后端规约
     */

    /**
     * 6.【强制】 对于需要使用超大整数的场景， 服务端一律使用 String 字符串类型返回， 禁止使用 Long 类型。
     * 说明： Java 服务端如果直接返回 Long 整型数据给前端， Javascript 会自动转换为 Number 类型（注： 此类型为双精度浮
     * 点数， 表示原理与取值范围等同于 Java 中的 Double）。 Long 类型能表示的最大值是 263-1， 在取值范围之内， 超过 253
     * （9007199254740992） 的数值转化为 Javascript 的 Number 时， 有些数值会产生精度损失。 扩展说明， 在 Long 取值范
     * 围内， 任何 2 的指数次的整数都是绝对不会存在精度损失的， 所以说精度损失是一个概率问题。 若浮点数尾数位与指数位
     * 空间不限， 则可以精确表示任何整数， 但很不幸， 双精度浮点数的尾数位只有 52 位。
     * 反例： 通常在订单号或交易号大于等于 16 位， 大概率会出现前后端订单数据不一致的情况。
     * 比如， 后端传输的 "orderId"： 362909601374617692， 前端拿到的值却是： 362909601374617660
     *
     * 在一些场景下，特别是在分布式系统中或者涉及到较大的数值时，如果直接使用long类型返回给前端，可能会出现精度损失的问题。为了避免这种问题，建议使用String类型来表示超大整数，因为字符串类型不会受到精度限制，并且能够保证数据的精确性。
     *
     * JavaScript 的 Number 类型是双精度浮点数，精度有限。
     * Long 类型能表示的最大值为 2^63-1，超过 2^53 的数值可能会在转换为 Number 时丢失精度。
     *
     * 使用 String 类型返回超大整数
     *
     * 这是最简单有效的解决方案，避免精度损失问题。
     * 可以使用 @JsonFormat 注解将 Long 类型属性格式化为 String 类型。
     *
     * @JsonFormat(shape = JsonFormat.Shape.STRING)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 10.【强制】 服务器内部重定向必须使用 forward； 外部重定向地址必须使用 URL 统一代理模块生成， 否
     * 则会因线上采用 HTTPS 协议而导致浏览器提示“不安全” ， 并且还会带来 URL 维护不一致的问题。
     */

    // 使用 forward 进行内部重定向
    @WebServlet("/login")
    public class LoginServlet extends HttpServlet {
        @SneakyThrows
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    // 使用 URL 代理模块生成外部重定向地址
    @WebServlet("/logout")
    public class LogoutServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            //String redirectUrl = UrlProxy.getRedirectUrl("https://www.example.com/");
            //response.sendRedirect(redirectUrl);
        }
    }

    /**
     * 二、异常日志-(二) 异常处理
     */

    /**
     * 6.【强制】finally 块必须对资源对象、流对象进行关闭，有异常也要做 try-catch。
     * 说明：如果 JDK7，可以使用 try-with-resources 方式。
     *
     * try-with-resources 是 JDK7 中引入的一个新特性，用于简化资源管理。它可以自动关闭在 try 语句块中声明的资源，从而避免资源泄漏。
     *
     * try-with-resources 工作原理：
     *
     * 在 try 语句块中声明的资源必须实现 AutoCloseable或者Closeable 接口。
     * try 语句块执行完毕后，JVM 会自动调用资源的 close() 方法，即使发生异常也会执行。
     * finally 语句块中的代码可以省略，因为它会被 JVM 自动执行。
     * try-with-resources 的优势：
     *
     * 简化资源管理：无需手动关闭资源，避免资源泄漏。
     * 提高代码可读性：代码更加简洁易懂。
     * 增强代码健壮性：即使发生异常，资源也能被正确关闭。
     */

    public void ffo() {
        /*
        FileReader 和 BufferedReader 都实现了 AutoCloseable 接口。
        try 语句块执行完毕后，JVM 会自动关闭 reader 和 br。
        即使发生异常，reader 和 br 也会被正确关闭。
         */
        try (FileReader reader = new FileReader("file.txt");
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
