package com.example.demo;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.demo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-27 10:52
 * To change this template use File | Settings | File and Code Templates.
 */

@SpringBootTest
@Slf4j
public class OkHttpTest {

    @Autowired
    private OkHttpClient okHttpClient;

    @Test
    public void okHttpTest() throws IOException {
        // 发送请求并处理响应
        for (int i = 0; i < 10; i++) {
            // 创建 OkHttpClient 实例
            //OkHttpClient client = new OkHttpClient();

            // 构建请求体
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            String jsonBody = "{\"appId\":\"21762000921804636162\",\"bizData\":\"{\\\"signNum\\\":\\\"2705wxl00002\\\",\\\"cerNum\\\":\\\"9fb11ce622839ed0ee3efa76fb433aa6\\\",\\\"phone\\\":\\\"\\\",\\\"acctNum\\\":\\\"9fb11ce622839ed0ee3efa76fb433aa6\\\",\\\"reqTraceNum\\\":\\\"202403291008301714\\\",\\\"name\\\":\\\"\\\",\\\"bindType\\\":\\\"8\\\",\\\"bizOrderCode\\\":\\\"1711678112437\\\",\\\"cerType\\\":\\\"1\\\",\\\"memberRole\\\":\\\"分销方\\\"}\",\"charset\":\"UTF-8\",\"format\":\"json\",\"sign\":\"MEQCIAjAGxU600yhUmdjfmtx/B1RTjbZlSIzgcxwXMN2/+s8AiAbm2IcI+2MA4znZSavDwNWLad2RCi6qF5VMTBFH2kGrg==\",\"signType\":\"SM3withSM2\",\"transCode\":\"1010\",\"transDate\":\"20240329\",\"transTime\":\"100832\",\"version\":\"1.0\"}";
            RequestBody requestBody = RequestBody.create(jsonBody, mediaType);

            // 构建请求
            Request request1 = new Request.Builder()
                    .url("http://116.228.64.55:28082/yst-service-api/tm/handle")
                    .header("Content-Type", "application/json; charset=utf-8")
                    .post(requestBody)
                    .build();

            try (Response response = okHttpClient.newCall(request1).execute()) {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    log.info("Response: {}", responseBody);
                } else {
                    log.info("Request failed: {} - {}", response.code(), response.message());
                }
            }

            // 获取连接池活动连接数
            ConnectionPool pool = okHttpClient.connectionPool();
            int activeConnections = pool.connectionCount();
            log.info("Active connections in connection pool: {}", activeConnections);
            // 把 keepAliveDuration 设定为3s 可以看到连接池中的RealConnection的(HashCode内存地址)
            // 表明如果在3s内调用会持续复用，超过3s后会重新创建RealConnection!!!
        }
    }




}
