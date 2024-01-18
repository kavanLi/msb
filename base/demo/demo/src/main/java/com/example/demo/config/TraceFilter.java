package com.example.demo.config;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 这个代码实现了一个自定义的过滤器,主要功能是:

 生成一个追踪id,并存入MDC(Mapped Diagnostic Context,诊断映射上下文)。
 计算请求的处理时间,也就是从过滤器开始到结束的时间间隔。
 将追踪id和请求时间打印到日志中。
 主要逻辑是:

 获取请求头中的追踪id,如果不存在就自动生成一个;
 将生成的或者获取的追踪id保存到MDC;
 调用链中的下一个过滤器;
 最后清除MDC,并打印日志输出请求处理时间。
 这样做的好处是:

 可以为每个请求生成一个唯一的追踪id,方便跟踪请求日志。
 计算每个请求的处理时间,可以方便监控系统性能。
 将追踪id和时间记录到日志中,进行后期分析。
 通过MDC避免在业务代码中传递追踪id等变量。
 总之,这是一个典型的用过滤器实现日志追踪和监控性能的例子,主要用于记录和分析请求信息。

 在Java应用中，使用MDC（Mapped Diagnostic Context）来将traceId放入日志中是一种常见的做法，特别适用于多线程环境和分布式系统。MDC允许你将自定义的信息关联到日志中，以便在整个请求处理过程中跟踪和记录。

 首先，确保你的项目中已经引入了SLF4J和相应的日志实现（比如Logback）。然后，你可以使用MDC来实现traceId的传递和记录。

 请确保在实际项目中，你的日志配置（例如logback.xml）包含了%X{traceId}这样的占位符，以确保traceId被正确地输出到日志中。例如：

 <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
 <encoder>
 <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - traceId:%X{traceId} - %msg%n</pattern>
 </encoder>
 </appender>

 */
@Component
@Slf4j
public class TraceFilter extends OncePerRequestFilter {

    private static final String TRACE_ID_HEADER = "x-traceId-header";

    private static final String LOG_TRACE_ID = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        try {
            String traceId = request.getHeader(TRACE_ID_HEADER);
            if (StringUtils.isEmpty(traceId)) {
                traceId = UUID.randomUUID().toString();
                MDC.put(LOG_TRACE_ID, traceId);
                filterChain.doFilter(request, response);
            }

        } finally {
            long cost = System.currentTimeMillis() - start;
            log.info("this request cost {} ms.", cost);
            MDC.clear();
        }
    }

    /**
     * 以下是一个简单的Java代码案例，演示了如何使用MDC在日志中记录traceId：
     */
    // 生成 traceId 的方法，这里简单地使用当前时间戳
    private static String generateTraceId() {
        return String.valueOf(System.currentTimeMillis());
    }

    // 处理请求的方法，将 traceId 放入 MDC，然后进行日志记录
    private static void processRequest(String traceId) {
        // 将 traceId 放入 MDC
        MDC.put("traceId", traceId);
        // 在日志中记录带有 traceId 的信息
        log.info("Processing request...");
        // 模拟处理过程
        // ...
        // 清理 MDC
        MDC.clear();
    }

    public static void main(String[] args) {
        // 生成 traceId
        String traceId = generateTraceId();
        // 处理请求
        processRequest(traceId);
    }
    /**
     * 以上是一个简单的Java代码案例，演示了如何使用MDC在日志中记录traceId：
     */

}
