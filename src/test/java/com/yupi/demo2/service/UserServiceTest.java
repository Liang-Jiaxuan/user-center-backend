package com.yupi.demo2.service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.yupi.demo2.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("dogLjx");
        user.setUserAccount("123");
        user.setAvatarUrl("C:\\Users\\16179\\Desktop\\SpringBoot2\\11.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    public void testLeetCode(){
        int[] nums = {1,2,3,7,65};
        System.out.println(nums);
    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "yu&pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "dogLjx";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertEquals(-1,result);
        userAccount = "Ljx666";
        result = userService.userRegister(userAccount, userPassword, checkPassword,planetCode);
        Assertions.assertTrue(result>0);
    }

    @Test
    public void testSearchUsersByTags(){
        List<String> tagNameList = Arrays.asList("java","python");
        List<User> userList = userService.searchUsersByTags(tagNameList);
        Assertions.assertNotNull(userList);
    }
}