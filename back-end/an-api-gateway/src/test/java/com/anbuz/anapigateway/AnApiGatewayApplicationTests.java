package com.anbuz.anapigateway;

import com.anbuz.anapibackend.service.InterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnApiGatewayApplicationTests {

    @DubboReference
    InterfaceInfoService interfaceInfoService;

    @Test
    void testDubbo() {
        System.out.println(interfaceInfoService.list());
    }

}
