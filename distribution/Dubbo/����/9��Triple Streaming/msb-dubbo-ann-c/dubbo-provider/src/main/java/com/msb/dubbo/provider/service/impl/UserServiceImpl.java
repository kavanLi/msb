package com.msb.dubbo.provider.service.impl;

import com.msb.dubbo.bean.User;
import com.msb.dubbo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.protocol.tri.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// 定义一个Dubbo服务
@Slf4j
@DubboService
public class UserServiceImpl  implements IUserService {


    @Override
    public User getUserById( Long id) {
        log.info("获取用户信息 userId:{}",id);
        User user = User.builder().id(id)
                .age(12)
                .name("天涯")
                .build();
        return user;
    }


    @Override
    public void sayHelloServerStream(String name, StreamObserver<String> reponse) {

        reponse.onNext("hello 1");
        sleep();
        reponse.onNext("hello 2");
        sleep();
        reponse.onNext("hello 3");
        sleep();
        reponse.onNext("hello 4");
        sleep();
        reponse.onNext("hello 5");
        sleep();
        reponse.onNext("hello 6");
        reponse.onCompleted();
    }
    public void sleep(){
        try {
            Thread.sleep(3 *1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public StreamObserver<String> sayHelloStream(StreamObserver<String> response) {
        return new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                //接收客户端发送过来的数据，进行处理
                sleep();
                System.out.println("接收到客户端发送的数据：" + data);
                response.onNext("服务端将数据处理后返回：" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("异常处理");
            }

            @Override
            public void onCompleted() {
                System.out.println("服务端处理完毕");
            }
        };
    }
}
