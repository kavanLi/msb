package com.mashibing.internal.common.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-01 10:53
 * To change this template use File | Settings | File and Code Templates.
 */
public class OkHttpRetryInterceptor implements Interceptor {
    private int maxRetries;
    private long initialRetryInterval;

    public OkHttpRetryInterceptor(int maxRetries, long initialRetryInterval) {
        this.maxRetries = maxRetries;
        this.initialRetryInterval = initialRetryInterval;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        IOException ioException = null;
        long retryInterval = initialRetryInterval;

        // 执行最大重试次数
        for (int retry = 0; retry < maxRetries; retry++) {
            try {
                // 发起请求
                response = chain.proceed(request);
                // 如果响应码不是5xx，则直接返回响应
                if (!response.isSuccessful() && response.code() < 500) {
                    return response;
                }
                // 如果响应码是5xx，则等待一段时间后重试
                Thread.sleep(retryInterval);
                // 重试间隔时间翻倍 指数退避的重试策略
                retryInterval *= 2;
            } catch (IOException e) {
                // 保存异常，稍后再重试
                ioException = e;
                // 等待一段时间后重试
                try {
                    Thread.sleep(retryInterval);
                } catch (InterruptedException ex) {
                }
                // 重试间隔时间翻倍
                retryInterval *= 2;
            } catch (InterruptedException e) {
                // 忽略线程中断异常
                Thread.currentThread().interrupt();
            }
        }

        // 如果最大重试次数仍然失败，则抛出异常
        if (ioException != null) {
            throw ioException;
        }

        // 如果响应为null，则抛出异常
        if (response == null) {
            throw new IOException("No response after max retries");
        }

        return response;
    }
}

