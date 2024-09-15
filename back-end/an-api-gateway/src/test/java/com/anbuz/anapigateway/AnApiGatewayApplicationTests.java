package com.anbuz.anapigateway;

import com.anbuz.anapibackend.service.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnApiGatewayApplicationTests {

    @DubboReference
    UserService userService;

    @Test
    void testDubbo() {
        System.out.println(userService.getUserByAccessKey("12345"));
    }

}
