package com.msb.webflux;

import com.msb.webflux.handle.OrderHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class WebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(OrderHandler handler) {
        return
                // 包含两个参数：1，测试条件是否通过，如果通过，则路由到第二个参数指定的路由函数
                nest(
                        // 判断请求路径是否匹配指定的模式，（请求路径前缀）
                        path("/orders"),
                        // 如果匹配通过，则路由到该路由函数
                        nest(accept(APPLICATION_JSON), // 判断请求的报文头字段accept是否匹配APPLICATION_JSON媒体类型
                                // 如果匹配，则路由到下面的路由函数：将/orders/{id}路由给handler的get方法
                                route(GET("/{id}"), handler::get)
                                        // 如果是get请求/orders，则路由到handler的list
                                        .andRoute(method(HttpMethod.GET), handler::list))
                                // 如果contentType匹配，并且路径匹配/orders，则路由到路由函数
                                // 如果是POST请求/orders，则路由到handler的create
                                .andNest(contentType(APPLICATION_JSON ), route(POST("/"), handler::create))
                );
    }


}
