package com.yupi.demo2.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test(){
        //list
        List<String> list = new ArrayList<>();
        list.add("aaa");
        System.out.println("list:" + list.get(0));
        //list.remove(0);

        RList<String> rList = redissonClient.getList("test-list");
        rList.add("bbb");
        System.out.println("rList:" + rList.get(0));
        //rList.remove(0);
    }
}
