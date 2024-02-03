package com.msb.reactor;

import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ReactorDemo3 {
    public static String httpRequest()throws IOException {
        URL url = new URL("https://www.mashibing.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();;
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String temp = null;
        StringBuffer stringBuffer = new StringBuffer();
        while ((temp = reader.readLine()) != null){
            stringBuffer.append(temp).append("\r\n");
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        // 1、发送http请求
//        Mono.fromCallable(() ->httpRequest())
//                .subscribe(System.out::println);
        // 2、使用Java8 方法引用
//        Mono.fromCallable(ReactorDemo3::httpRequest)
//                .subscribe(System.out::println);
        //3、上述代码不仅发送了http请求，还要处理onError
        Mono.fromCallable(ReactorDemo3::httpRequest)
                .subscribe(
                        item -> System.out.println(item),
                        ex -> System.err.println("请求异常："+ ex.toString()),
                        () -> System.out.println("请求结束")
                        );
    }
}
