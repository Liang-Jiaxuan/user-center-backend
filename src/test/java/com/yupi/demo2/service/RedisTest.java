package com.yupi.demo2.service;

import com.yupi.demo2.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void test(){
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        //增
        valueOperations.set("lString","dog");
//        valueOperations.set("lInt",1);
//        valueOperations.set("lDouble",2.0);
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("ljx");
//        valueOperations.set("lUser",user);
        //查
        Object l = valueOperations.get("lString");
        Assertions.assertTrue("dog".equals((String) l));

    }
}
