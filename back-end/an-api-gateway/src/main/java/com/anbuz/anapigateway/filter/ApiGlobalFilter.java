package com.anbuz.anapigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * 网关全局过滤器
 */
@Slf4j
@Component
public class ApiGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求信息
        URI uri = exchange.getRequest().getURI();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        // 打日志
        log.info("Received request URI: {} from {}", uri, remoteAddress);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            HttpStatus status = exchange.getResponse().getStatusCode();
            // 记录响应数据
            if (status == HttpStatus.OK){
                log.info("Global Post Filter: {} from {}", uri, remoteAddress);
            }else{
                log.warn("Non-OK Response, Code: {} ,URI: {}, from {}", status,  uri, remoteAddress);
            }
        }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
