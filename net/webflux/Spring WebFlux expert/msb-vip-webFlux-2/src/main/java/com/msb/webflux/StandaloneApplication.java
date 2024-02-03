package com.msb.webflux;

import com.msb.webflux.entity.PasswordDTO;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class StandaloneApplication {
    static Logger LOGGER = LoggerFactory.getLogger(StandaloneApplication.class);

    public static void main(String... args) {
        long start = System.currentTimeMillis();
        // 调用routes 方法，然后将RouterFunction 转换为HttpHandler。
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(routes(
                // BCrypt算法进行18 轮散列，这可能需要几秒钟来编码/匹配
                new BCryptPasswordEncoder(18)
        ));
        // 内置HttpHandler适配器
        ReactorHttpHandlerAdapter reactorHttpHandler = new ReactorHttpHandlerAdapter(httpHandler);
        // 创建HttpServer实例，它是ReactorNetty API的一部分。
        DisposableServer server = HttpServer.create()
                .host("localhost") // 配置host
                .port(8080) // 配置端口
                .handle(reactorHttpHandler) // 指定handler
                .bindNow(); // 调用bindNow启动服务器

        LOGGER.debug("Started in " + (System.currentTimeMillis() - start) + " ms");
        // 为了使应用程序保持活动状态，阻塞主Thread 并监听服务器的处理事件
        server.onDispose().block();
    }

    public static RouterFunction<ServerResponse> routes(PasswordEncoder passwordEncoder) {
        return
                // 使用/ check 路径处理任何POST 方法的请求
                route(POST("/password"),
                        request -> request
                                .bodyToMono(PasswordDTO.class)
                                .doOnNext(System.out::println)
                                // 使用PasswordEncoder检查已加密密码的原始密码
                                //
                                .map(p -> passwordEncoder.matches(p.getRaw(), p.getSecured()))
                                // 如果密码与存储的密码匹配
                                // 则ServerResponse 将返回OK状态(200)
                                // 否则，返回EXPECTATION_FAILED(417)
                                .flatMap(isMatched -> isMatched
                                        ? ServerResponse.ok()
                                        .build()
                                        : ServerResponse.status(HttpStatus.EXPECTATION_FAILED)
                                        .build()
                                )
                );
    }
}
