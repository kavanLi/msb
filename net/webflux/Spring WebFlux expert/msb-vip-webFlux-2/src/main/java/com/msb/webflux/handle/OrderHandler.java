package com.msb.webflux.handle;

import com.msb.webflux.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderHandler {

    private Map<String,Order> orderMap  = new HashMap<>();

    public Mono<ServerResponse> create(ServerRequest request) {
      return request.bodyToMono(Order.class)
               .doOnNext(order -> {
                   orderMap.put(order.getId(),order);
               })
               .flatMap(order -> ServerResponse.created(URI.create("/order/" +order.getId()))
                       .build()
               );

    }

    public Mono<ServerResponse> get(ServerRequest request) {
        String id = request.pathVariable("id");
        Order order1 = orderMap.get(id);
        return Mono.just(order1)
                .flatMap(order ->
                        ServerResponse
                                .ok()
                                .syncBody(order)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return  Mono.just(orderMap.values()
                .stream()
                .collect(Collectors.toList()))
                .flatMap(order ->
                        ServerResponse
                                .ok()
                                .syncBody(order)
                )
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}