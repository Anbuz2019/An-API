package com.anbuz.anapigateway.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.anbuz.anapicommon.service.inner.InnerInterfaceInfoService;
import com.anbuz.anapicommon.service.inner.InnerUserInterfaceInvokeService;
import com.anbuz.anapicommon.service.inner.InnerUserService;
import com.anbuz.anapicommon.utils.SignUtils;
import com.anbuz.anapigateway.exception.GatewayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
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
    InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    InnerUserService innerUserService;

    @DubboReference
    InnerUserInterfaceInvokeService innerUserInterfaceInvokeService;

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
//        return DataBufferUtils.join(exchange.getRequest().getBody())
//                .flatMap(buffer -> {
//                    String body = decodeBody(buffer);
//                    // 重新包装请求体
//                    ServerHttpRequest mutatedRequest = exchange.getRequest()
//                            .mutate()
//                            .build();
//                    ServerWebExchange mutatedExchange = exchange.mutate()
//                            .request(mutatedRequest)
//                            .build();
//
//                    // 验证请求信息
//                    return validateParameters(mutatedExchange, chain, body);
//                });
        Map<String, String> params = exchange.getRequest().getQueryParams().toSingleValueMap();
        String body = JSONUtil.toJsonStr(params);
        return validateParameters(exchange, chain, body);
    }

    private Mono<Void> validateParameters(ServerWebExchange exchange, GatewayFilterChain chain, String body) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String path = exchange.getRequest().getPath().toString();
        String method = exchange.getRequest().getMethodValue();
        String uri;
        try {
            uri = new URI(exchange.getRequest().getURI().toString().trim()).getPath();
        } catch (URISyntaxException e) {
            throw new GatewayException("uri格式不正确");
        }

        // 请求头中参数必须完整
        if (StringUtils.isAnyBlank(sign, accessKey, timestamp)) {
            throw new GatewayException("请求参数不完整");
        }
        // 防重发XHR
        long currentTime = System.currentTimeMillis() / 1000;
        if (timestamp == null || currentTime - Long.parseLong(timestamp) >= FIVE_MINUTES) {
            throw new GatewayException("已超时");
        }
        // 校验参数
        User user = innerUserService.getUserByAccessKey(accessKey);
        if (user == null || user.getSecretKey() == null) {
            throw new GatewayException("AK不正确");
        }
        if (user.getUserStatus() == 1) {
            throw new GatewayException("用户被封禁");
        }
        Map<String, String> header = new HashMap<>();
        header.put("accessKey", accessKey);
        header.put("method", method);
        header.put("url", path);
        header.put("body", body);
        header.put("timestamp", timestamp);
        String check = SignUtils.getSign(JSONUtil.toJsonStr(header), user.getSecretKey());
        if (!check.equals(sign)) {
            throw new GatewayException("签名不合法");
        }
        // 检查是否存在该接口
        InterfaceInfoVO interfaceInfo = innerInterfaceInfoService.getInterfaceByMethodAndURI(method, uri);
        if (interfaceInfo == null || interfaceInfo.getId() < 0 || interfaceInfo.getInterfaceStatus() == 1) {
            throw new GatewayException("接口不存在或已经关闭");
        }
        return invokeManage(exchange, chain, interfaceInfo, user);
    }

    /**
     * 处理调用的逻辑
     */
    private Mono<Void> invokeManage(ServerWebExchange exchange, GatewayFilterChain chain,
                                    InterfaceInfoVO interfaceInfo, User user) {
        // 调用次数加1
        boolean invoke = innerUserInterfaceInvokeService.invoke(user.getId(), interfaceInfo.getId());
        if (!invoke) {
            throw new GatewayException("调用失败");
        }
        return chain.filter(exchange);
    }

    private String decodeBody(DataBuffer buffer) {
        return StandardCharsets.UTF_8.decode(buffer.asByteBuffer()).toString();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
