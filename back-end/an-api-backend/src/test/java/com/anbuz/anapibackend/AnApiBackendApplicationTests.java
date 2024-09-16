package com.anbuz.anapibackend;

import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapibackend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AnApiBackendApplicationTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
        String s = DigestUtils.md5DigestAsHex("123456".getBytes());
        System.out.println(s);
    }

    @Test
    public void testSearchUserByTags() {
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchByTags(tagNameList);
        Assertions.assertNotNull(userList);
    }

    @Test
    public void testSDK() {

    }

}
