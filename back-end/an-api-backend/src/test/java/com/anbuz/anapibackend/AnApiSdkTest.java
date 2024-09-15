package com.anbuz.anapibackend;

import com.anbuz.anapisdk.model.request.BaseRequest;
import com.anbuz.anapisdk.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class AnApiSdkTest {

    @Resource
    ApiService anApiService;

    @Test
    void test() {
        BaseRequest request = new BaseRequest();
        Map<String, Object> body = new HashMap<>();
        body.put("name", "anbuz");
        request.setRequestParams(body);
        System.out.println(anApiService.NameRequest(request));
    }
}
