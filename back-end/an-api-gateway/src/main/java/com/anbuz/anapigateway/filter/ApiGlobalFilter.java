package com.anbuz.anapigateway.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.anbuz.anapibackend.model.entity.User;
import com.anbuz.anapibackend.model.vo.InterfaceInfoVO;
import com.anbuz.anapibackend.service.InterfaceInfoService;
import com.anbuz.anapibackend.service.UserInterfaceInvokeService;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapigateway.utils.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ApiGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    InterfaceInfoService interfaceInfoService;

    @DubboReference
    UserService userService;

    @DubboReference
    UserInterfaceInvokeService userInterfaceInvokeService;

    private static final Long FIVE_MINUTES = 5 * 60L;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 获取请求信息
        URI uri = exchange.getRequest().getURI();
        InetSocketAddress remoteAddress = exchange.getRequest().getRemoteAddress();
        exchange.getRequest().getPath();
        // 打日志
        log.info("Received request URI: {} from {}", uri, remoteAddress);

        // 缓存请求体以供后续使用
        // todo 其实也可以使用 exchange.getRequest().getQueryParams()
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .flatMap(buffer -> {
                    String body = decodeBody(buffer);
                    // 重新包装请求体
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .build();
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();

                    // 验证请求信息
                    return validateParameters(mutatedExchange, chain, body);
                });
    }

    private Mono<Void> validateParameters(ServerWebExchange exchange, GatewayFilterChain chain, String body) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethodValue();
        ServerHttpResponse response = exchange.getResponse();
        String uri;
        try {
            uri = new URI(exchange.getRequest().getURI().toString().trim()).getPath();
        } catch (URISyntaxException e) {
            return forbidden(response, "uri格式不正确");
        }

        // 请求头中参数必须完整
        if (StringUtils.isAnyBlank(sign, accessKey, timestamp)) {
            return forbidden(response, "请求参数不完整");
        }
        // 防重发XHR
        long currentTime = System.currentTimeMillis() / 1000;
        if (timestamp == null || currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            return forbidden(response, "已超时");
        }
        // 校验参数
        User user = userService.getUserByAccessKey(accessKey);
        if (user == null || user.getSecretKey() == null) {
            return forbidden(response, "AK不正确");
        }
        if (user.getUserStatus() == 1) {
            return forbidden(response, "用户被封禁");
        }
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        header.put("method", method);
        header.put("url", path);
        header.put("body", body);
        header.put("timestamp", timestamp);
        String check = SignUtils.getSign(JSONUtil.toJsonStr(header), user.getSecretKey());
        if (!check.equals(sign)) {
            return forbidden(response, "签名不合法");
        }
        // 检查是否存在该接口
        InterfaceInfoVO interfaceInfo = interfaceInfoService.getInterfaceByMethodAndURI(method, uri);
        if (interfaceInfo == null || interfaceInfo.getId() < 0 || interfaceInfo.getInterfaceStatus() == 1) {
            return forbidden(response, "接口不存在或已经关闭");
        }
        return invokeManage(exchange, chain, interfaceInfo, user);
    }

    /**
     * 处理调用的逻辑
     */
    private Mono<Void> invokeManage(ServerWebExchange exchange, GatewayFilterChain chain,
                                    InterfaceInfoVO interfaceInfo, User user) {
        // 调用次数加1
        boolean invoke = userInterfaceInvokeService.invoke(user.getId(), interfaceInfo.getId());
        if (!invoke) {
            return forbidden(exchange.getResponse(), "调用失败");
        }
        return chain.filter(exchange);
    }

    private String decodeBody(DataBuffer buffer) {
        return StandardCharsets.UTF_8.decode(buffer.asByteBuffer()).toString();
    }

    private static Mono<Void> forbidden(ServerHttpResponse response, String logInfo) {
        log.info("请求被拒绝，原因: {}", logInfo);
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
