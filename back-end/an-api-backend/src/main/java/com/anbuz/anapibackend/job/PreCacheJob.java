package com.anbuz.anapibackend.job;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.constant.RedissonLockConstant;
import com.anbuz.anapibackend.mapper.UserMapper;
import com.anbuz.anapibackend.model.entity.User;
import com.anbuz.anapibackend.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
//@Component
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 导入重点用户
    private final List<Long> mainUserList = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L);

    @Scheduled(cron = "0 */20 * * * ?")
    public void doCacheRecommendUser() {
        // 使用 Redisson 获取锁的对象
        RLock lock = redissonClient.getLock(RedissonLockConstant.PreCacheLock);
        try {
            // 只有一个线程能获取到锁
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                log.info("获得锁: {}", Thread.currentThread().getId());
                for (Long userId : mainUserList) {
                    // redis缓存命名
                    String redisKey = "anapibackend:user:recommendUsers:" + BaseContext.getCurrentUser().getId();
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    // 查询 MYSQL 用户表
                    List<User> userList = userService.matchUser(50);
                    // 将查到的内容记录在缓存中（过期时间为600秒）
                    try {
                        valueOperations.set(redisKey, userList, 600000L, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("Redis set key error: {}", e.getMessage());
                    }
                    log.info("预加载用户推荐信息成功");
                }
            }
        } catch (InterruptedException e) {
            log.error("Redis lock error: {}", e.getMessage());
        } finally {
            // 释放锁，只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("释放锁：{}", Thread.currentThread().getId());
            }
        }
    }
}

