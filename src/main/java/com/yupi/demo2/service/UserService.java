package com.yupi.demo2.service;

import com.yupi.demo2.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author 16179
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-05-18 20:38:30
 * 用户服务
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录态键
     */


    /**
     * 用户注释
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafeUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogOut(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);
}
