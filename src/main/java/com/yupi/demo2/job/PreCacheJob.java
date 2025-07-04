package com.yupi.demo2.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.demo2.mapper.UserMapper;
import com.yupi.demo2.model.User;
import com.yupi.demo2.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存预热任务
 */
@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedissonClient redissonClient;

    //重点用户
    List<Long> mainUserList = Arrays.asList(1L);

    // 每天执行, 预热推荐用户
    @Scheduled(cron = "0 56 19 * * *")
    public void doCacheRecommendUser(){
        RLock lock = redissonClient.getLock("yupao:precachejob:docache:lock");
        try {
            //只有一个线程能获取到锁
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)) {
                System.out.println("get lock " + Thread.currentThread().getId());
                for (Long userId : mainUserList){
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1, 20),queryWrapper);
                    String redisKey = String.format("yupao:user:recommend:%s",userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();

                    try {
                        valueOperations.set(redisKey, userPage, 10000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        System.out.println("redis set key error " + e);
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println("doCacheRecommendUser error" + e);
        } finally {
            //只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                System.out.println("unlock " + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }
}
