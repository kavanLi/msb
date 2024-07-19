package com.manzhizhen.spring.cloud.gateway.mini.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

/**
 * SpringCloudGateway重写url解决多斜线问题
 * @author: kavanLi-R7000
 * @create: 2024-06-04 15:56
 * To change this template use File | Settings | File and Code Templates.
 */
public class RewriteMultiSlashPathWebFilter implements WebFilter {

    /**
     * 解决双斜杠
     */
    private static final String DOUBLE_SLASH = "//";

    //@Override
    //public Mono <Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    //    ServerHttpRequest req = exchange.getRequest();
    //    String path = req.getURI().getRawPath();
    //
    //    if (!path.contains(DOUBLE_SLASH)) {
    //        return chain.filter(exchange);
    //    }
    //
    //    addOriginalRequestUrl(exchange, req.getURI());
    //    String newPath = path.replaceAll("[/]{2,}", "/");
    //    ServerHttpRequest request = req.mutate().path(newPath).build();
    //    exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
    //    return chain.filter(exchange.mutate().request(request).build());
    //}

    /**
     * 解决多斜杠
     */
    private static final String MULTIPLE_SLASH_REGEX = "([/])\\1+";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest req = exchange.getRequest();
        String path = req.getURI().getRawPath();

        if (!path.contains("/")) {
            return chain.filter(exchange);
        }

        addOriginalRequestUrl(exchange, req.getURI());
        String newPath = path.replaceAll(MULTIPLE_SLASH_REGEX, "/");
        ServerHttpRequest request = req.mutate().path(newPath).build();
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
        return chain.filter(exchange.mutate().request(request).build());
    }
}