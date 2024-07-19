package com.mashibing.internal.common.interceptor.feigh;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import javax.annotation.Resource;

import cn.hutool.core.util.StrUtil;
import com.mashibing.internal.common.config.TraceFilter;
import com.mashibing.internal.common.interceptor.feigh.log.DefaultLogInterceptor;
import com.mashibing.internal.common.interceptor.feigh.log.FeignLogInterceptor;
import com.mashibing.internal.common.interceptor.feigh.context.FeignContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.encoding.HttpEncoding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;


/**
 * @author: kavanLi-R7000
 * @create: 2024-04-12 13:45
 * To change this template use File | Settings | File and Code Templates.
 */
public class FeignInterceptor implements RequestInterceptor {

    @Bean()
    @ConditionalOnMissingBean(FeignLogInterceptor.class)
    public FeignLogInterceptor feignLogInterceptor() {
        return new DefaultLogInterceptor();
    }

    @Resource
    @Lazy
    private FeignLogInterceptor logInterceptor;

    @Override
    public void apply(RequestTemplate template) {
        template.header(HttpEncoding.CONTENT_TYPE, "application/json");
        String body = StrUtil.str(template.body(), StandardCharsets.UTF_8);
        //FeignLogInterceptor logInterceptor = FeignSpringContextHolder.getBean(FeignLogInterceptor.class);
        String interfaceName = template.methodMetadata().targetType().getName();
        String targetMethod = template.methodMetadata().configKey();
        String target = StrUtil.format("{}.{}", interfaceName, targetMethod);
        FeignContextHolder.setLocalTime();
        logInterceptor.request(target, template.request().url(), body);
        String traceId = MDC.get(TraceFilter.LOG_TRACE_ID);
        //传递日志traceId
        if (StrUtil.isNotEmpty(traceId)) {
            template.header(TraceFilter.TRACE_ID_HEADER, traceId);
        } else {
            template.header(TraceFilter.TRACE_ID_HEADER, UUID.randomUUID().toString());
        }
    }
}
