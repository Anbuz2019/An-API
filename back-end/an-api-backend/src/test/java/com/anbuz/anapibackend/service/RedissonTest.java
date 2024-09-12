package com.anbuz.anapibackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedissonTest {

    @Resource
    RedissonClient redissonClient;

    @Test
    void testRedisson() {
        Assertions.assertNotNull(redissonClient);

        // list
        List<String> list = new ArrayList<>();
        list.add("anbuz");
        list.add("zeyunic");
        list.remove(0);
        System.out.println(list);

        RList<String> rlist = redissonClient.getList("redisList");
        rlist.add("anbuz");
        rlist.add("zeyunic");
        rlist.remove(0);
        System.out.println(rlist);

        // map
        Map<String, Object> map = new HashMap<>();
        map.put("anbuz", 100);
        System.out.println(map.get("anbuz"));

        RMap<String, Object> rmap = redissonClient.getMap("redisMap");
        rmap.put("anbuz", 100);
        System.out.println(rmap.get("anbuz"));
    }

    @Test
    void testRedissonWatchDog() {
        // 使用 Redisson 获取锁的对象
        RLock lock = redissonClient.getLock("anapibackend:preCacheJob:doCache:lock");
        try {
            // 只有一个线程能获取到锁
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                Thread.sleep(1000000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁，只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}


