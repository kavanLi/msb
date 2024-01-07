package com.msb.dubbo.consumer.service;

import com.msb.dubbo.service.IUserService;
import com.msb.dubbo.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class OrderService {
    // 引用对应的dubbo服务
    @DubboReference(protocol = "tri")
    private IUserService iUserService;
    @Autowired
    private RestTemplate restTemplate;
    public String createOrder(Long userId) {
      //  User user = iUserService.getUserById(userId);
      //  User user = restTemplate.getForObject("http://localhost:20884/user/232", User.class);
       // log.info("用户信息：{}" ,user);
        // 服务端流处理
//        iUserService.sayHelloServerStream("李华", new StreamObserver<String>() {
//            @Override
//            public void onNext(String data) {
//                System.out.println("接收响应式流：" + data);
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println("异常处理");
//            }
//
//            @Override
//            public void onCompleted() {
//                System.out.println("响应数据完成");
//            }
//        });
        // 双端流
        StreamObserver<String> streamObserver = iUserService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接收到响应数据：" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("异常处理");
            }

            @Override
            public void onCompleted() {
                System.out.println("接收到相应数据完成");
            }
        });


        streamObserver.onNext("request hello 1");
        streamObserver.onNext("request hello 2");
        streamObserver.onNext("request hello 3");
        streamObserver.onNext("request hello 4");
        streamObserver.onCompleted();
        return "创建订单成功";
    }
}
