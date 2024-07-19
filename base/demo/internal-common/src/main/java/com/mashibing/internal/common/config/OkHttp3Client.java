package com.mashibing.internal.common.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: kavanLi-R7000
 * @create: 2024-05-15 16:35
 * To change this template use File | Settings | File and Code Templates.
 */

@Service
@Slf4j
public class OkHttp3Client {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 使用案例：
     *     public Map <Object, Object> getWxPhoneNumber(String code) {
     *         log.info("获取手机号 ***start***");
     *         // 一组参数名和参数值
     *         WxAccessToken wxAccessTokenFromCache = getWxAccessTokenFromCache(wxAppId);
     *         String[] paramNames = {"access_token"};
     *         String[] paramValues = {wxAccessTokenFromCache.getWxAccessToken()};
     *
     *         String[] jsonBodyNames = {"code"};
     *         String[] jsonBodyValues = {code};
     *
     *         String responseBody = okHttp3Client.okhttpPost(WxConstants.GET_WX_ACCESS_TOKEN_API_URL, readOkHttpClient,
     *                 OkHttp3Client.buildJsonBodyString(jsonBodyNames, jsonBodyValues),
     *                 OkHttp3Client.buildQueryParamsMap(paramNames, paramValues));
     *         log.info("获取手机号 ***end***");
     *         return null;
     *     }
     *
     */


    public static Map <String, String> buildQueryParamsMap(String[] paramNames, String[] paramValues) {
        // 使用Stream API和Collectors来生成Map<String, String>
        Map <String, String> queryParams = Stream.iterate(0, i -> i + 1)
                .limit(Math.min(paramNames.length, paramValues.length))
                .collect(Collectors.toMap(
                        i -> paramNames[i],
                        i -> paramValues[i]
                ));
        return queryParams;
    }

    public static String buildJsonBodyString(String[] paramNames, String[] paramValues) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> jsonMap = new HashMap <>();
        for (int i = 0; i < paramNames.length; i++) {
            jsonMap.put(paramNames[i], paramValues[i]);
        }
        try {
            return objectMapper.writeValueAsString(jsonMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String okhttpPost(String requestUrl, OkHttpClient okHttpClient, String jsonBody, Map<String, String> queryParams) throws IOException {
        log.info("Start the okHttp request");

        // jsonBody = JSON.toJSONString(Object);

        // 构建请求URL
        if (!CollectionUtils.isEmpty(queryParams)) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            requestUrl = urlBuilder.build().toString();
        }

        // 构建请求体
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonBody, mediaType);

        log.info("okhttpPost 请求url：{}", requestUrl);
        log.info("okhttpPost 请求参数：{}", jsonBody);

        // 构建请求
        Request okHttpRequest = new Request.Builder()
                .url(requestUrl)
                .header("Content-Type", "application/json; charset=utf-8")
                .post(requestBody)
                .build();
        log.info("okHttpRequest: {}", okHttpRequest);

        return newCall(okHttpClient, okHttpRequest);
    }

    private String okhttpGet(String requestUrl, OkHttpClient okHttpClient, Map<String, String> queryParams) throws IOException {
        // 构建请求URL
        if (!CollectionUtils.isEmpty(queryParams)) {
            HttpUrl.Builder urlBuilder = HttpUrl.parse(requestUrl).newBuilder();
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
            }
            requestUrl = urlBuilder.build().toString();
        }

        log.info("okhttpGet 请求参数：{}", requestUrl);

        // 构建请求
        Request okHttpRequest = new Request.Builder()
                .url(requestUrl)
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        log.info("okHttpRequest: {}", okHttpRequest);

        // 开始请求
        return newCall(okHttpClient, okHttpRequest);
    }

    @NotNull
    private String newCall(OkHttpClient okHttpClient, Request okHttpRequest) throws IOException {
        // 开始请求
        String responseBody = "";
        try (Response response = okHttpClient.newCall(okHttpRequest).execute()) {
            if (response.isSuccessful()) {
                responseBody = response.body().string();
                log.info("Response: {}", responseBody);
            } else {
                log.info("Request failed: {} - {}", response.code(), response.message());
            }
        }

        // 获取连接池活动连接数
        ConnectionPool pool = okHttpClient.connectionPool();
        int activeConnections = pool.connectionCount();
        log.info("Active connections in connection pool: {}", activeConnections);

        // 处理返回结果
        log.info("Processing response result");
        log.info("response:{}", responseBody);
        return responseBody;
    }
}
