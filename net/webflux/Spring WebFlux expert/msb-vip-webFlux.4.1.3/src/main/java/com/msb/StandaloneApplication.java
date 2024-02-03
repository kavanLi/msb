package com.msb;

import com.msb.bean.PasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class StandaloneApplication {
    static Logger LOGGER = LoggerFactory.getLogger(StandaloneApplication.class);
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // 调用Routes方法，然后将RouteFunction转化为HttpHandler
       HttpHandler handler =  RouterFunctions.toHttpHandler(routes(
                // BCryt算法进行18次三列， 这里需要耗时几秒
           new BCryptPasswordEncoder(18)
        ));
       // 内置HttpHandler适配器
        ReactorHttpHandlerAdapter reactorAdapter = new ReactorHttpHandlerAdapter(handler);
        // 创建HTTPServer实例，它是ReatorNettyAPI一部分
        DisposableServer server = HttpServer.create()
                .host("localhost")
                .port(8080) // 配置端口
                .handle(reactorAdapter)  // 指定handler
                .bindNow();// 调用bindNow 启动服务
        LOGGER.info("started in" + (System.currentTimeMillis() - start) + "ms");
        // 为了是使应用程序保持活动状态，阻塞Thread,并监听服务器处理事件
        server.onDispose().block();
    }

    private static RouterFunction<?> routes(BCryptPasswordEncoder passwordEncoder) {
        return
                //匹配请求POST 并且路径是password
                route(POST("password"),
                        reqeust -> reqeust
                                .bodyToMono(PasswordDTO.class)
                                .doOnNext(System.out::println)
                                // BCryptPasswordEncoder检查已加密的原始密码，加密密码
                                .map(p -> passwordEncoder.matches(p.getRaw(),p.getSecured()))
                                // 如果秘密匹配成功过 则OK
                                // 否则EXPECTATION_FAILED
                                .flatMap(isMatched -> isMatched ? ServerResponse.ok().build():
                                        ServerResponse.status(HttpStatus.EXPECTATION_FAILED).build()));
    }
}
