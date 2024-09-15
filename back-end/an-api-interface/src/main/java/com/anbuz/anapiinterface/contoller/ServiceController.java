package com.anbuz.anapiinterface.contoller;

import com.anbuz.anapiinterface.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@Slf4j
public class ServiceController {
    @GetMapping("/name")
    public BaseResponse<String> getName(String name) {
        return BaseResponse.success("你的名字是：" + name);
    }
}
