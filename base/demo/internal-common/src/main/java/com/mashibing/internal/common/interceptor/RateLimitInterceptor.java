package com.mashibing.internal.common.interceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.util.concurrent.RateLimiter;
import com.mashibing.internal.common.annotation.RateLimit;
import com.mashibing.internal.common.constant.ServerRespCode;
import com.mashibing.internal.common.exception.SmartyunstException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2024-06-26 14:53
 * To change this template use File | Settings | File and Code Templates.
 *
 * 参考：https://youthme.cn/110.html
 * 自定义接口限流拦截器
 */
@Component
@Aspect
public class RateLimitInterceptor {
    private final Map <String, RateLimiter> rateLimiterMap = new ConcurrentHashMap <>();


    /**
     * 假设 rateLimit.interval() 的值是 3：
     *
     * RateLimiter.create(1.0 / 3) 创建一个每3秒发放一个令牌的 RateLimiter 实例。
     * computeIfAbsent 方法将该 RateLimiter 实例与 methodName 关联，并存储在 rateLimiterMap 中，以便后续请求可以复用。
     * 这样可以确保每个被拦截的方法都有一个独立的 RateLimiter 实例，且限流策略根据注解参数动态配置。
     * @param point
     * @param rateLimit
     * @return
     * @throws Throwable
     */
    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint point, RateLimit rateLimit) throws Throwable {
        String methodName = point.getSignature().toShortString();
        RateLimiter rateLimiter = rateLimiterMap.computeIfAbsent(methodName, k -> RateLimiter.create(1.0 / rateLimit.interval()));

        if (rateLimiter.tryAcquire()) {
            return point.proceed();
        } else {
            if (rateLimit.throwErrorOnLimit()) {
                throw new SmartyunstException(ServerRespCode.FAIL, rateLimit.message());
            } else {
                //throw new RuntimeException("接口限流，请稍后再试！");
                //throw new SmartyunstException(ServerRespCode.FAIL, "接口限流，请稍后再试！");
                throw new SmartyunstException(ServerRespCode.SUCCESS, rateLimit.message());
            }
        }
    }

}
