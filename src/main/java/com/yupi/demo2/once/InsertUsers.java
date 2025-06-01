package com.yupi.demo2.once;
import java.util.Date;

import com.yupi.demo2.mapper.UserMapper;
import com.yupi.demo2.model.User;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

    /**
     * 批量插入用户
     */
//    @Scheduled(fixedDelay = 5000)
//    public void doInsertUsers(){
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        final int INSERT_NUM = 1000;
//        for (int i = 0; i < INSERT_NUM; i++) {
//            User user = new User();
//            user.setPlanetCode("11111111");
//            user.setUsername("假用户");
//            user.setUserAccount("fakeUser");
//            user.setAvatarUrl("");
//            user.setGender(0);
//            user.setUserPassword("12345678");
//            user.setPhone("123");
//            user.setEmail("123@qq.com");
//            user.setUserStatus(0);
//            user.setUserRole(0);
//            user.setTags("[]");
//            userMapper.insert(user);
//        }
//        stopWatch.stop();
//        stopWatch.getTotalTimeMillis();
//    }
}
