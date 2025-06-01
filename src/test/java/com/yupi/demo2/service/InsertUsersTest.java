package com.yupi.demo2.service;

import com.yupi.demo2.mapper.UserMapper;
import com.yupi.demo2.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUsersTest {
    @Resource
    private UserService userService;

    private ExecutorService executorService = new ThreadPoolExecutor(60, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));

    /**
     * 批量插入用户
     */
    @Test
    public void doInsertUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setPlanetCode("11111111");
            user.setUsername("假用户");
            user.setUserAccount("fakeUser");
            user.setAvatarUrl("");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123");
            user.setEmail("123@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setTags("[]");
            userList.add(user);
        }
        userService.saveBatch(userList, 10000);
        stopWatch.stop();
        stopWatch.getTotalTimeMillis();
    }


    /**
     * 并发批量插入用户
     */
    @Test
    public void doConcurrencyInsertUsers(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        //分十组
        int batchSize = 5000;
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setPlanetCode("11111111");
                user.setUsername("假用户");
                user.setUserAccount("fakeUser");
                user.setAvatarUrl("");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("123");
                user.setEmail("123@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setTags("[]");
                userList.add(user);
                if (j % batchSize == 0){
                    break;
                }
            }
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, batchSize);
            }, executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        stopWatch.getTotalTimeMillis();
    }

}
