package com.anbuz.anapigateway;

import com.anbuz.anapicommon.service.inner.InnerUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnApiGatewayApplicationTests {

    @DubboReference
    InnerUserService innerUserService;

    @Test
    void testDubbo() {
        System.out.println(innerUserService.getUserByAccessKey("12345"));
    }
}
