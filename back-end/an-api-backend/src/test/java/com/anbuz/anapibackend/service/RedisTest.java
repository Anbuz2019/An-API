package com.anbuz.anapibackend.service;

import com.anbuz.anapicommon.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate myredisTemplate;

    @Test
    void testRedis(){
//        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
//        redisTemplate.setValueSerializer(redisTemplate.getHashValueSerializer());
        ValueOperations valueOperations = myredisTemplate.opsForValue();
        valueOperations.set("anbuz1", "123");
        valueOperations.set("anbuz2", 1);
        valueOperations.set("anbuz3", 2.0);
        valueOperations.set("anbuz4", new User());
    }
}
