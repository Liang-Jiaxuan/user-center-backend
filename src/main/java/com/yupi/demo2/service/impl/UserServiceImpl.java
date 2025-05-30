package com.yupi.demo2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.demo2.common.ErrorCode;
import com.yupi.demo2.exception.BusinessException;
import com.yupi.demo2.model.User;
import com.yupi.demo2.service.UserService;
import com.yupi.demo2.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yupi.demo2.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现类
* @author 16179
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-05-18 20:38:30
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     */
    private static final String SALT = "yupi";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {

        //1 校验
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if (userPassword.length()<8||checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        if (planetCode.length()>5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return -1;
        }
        if (!userPassword.equals(checkPassword)){
            return -1;
        }
        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //2 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //3 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1 校验
        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if (userAccount.length()<4){
            return null;
        }
        if (userPassword.length()<8){
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()){
            return null;
        }
        //2 加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user==null){
            System.out.println("user login failed, userAccount cannot match userPassword");
            return null;
        }

        //4 用户脱敏
        User safeUser = getSafeUser(user);

        //3 记录用户的登录态
        HttpSession session = request.getSession();
        session.setAttribute(USER_LOGIN_STATE,safeUser);

        return safeUser;
    }

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    @Override
    public User getSafeUser(User originUser){
        User safeUser = new User();
        safeUser.setId(originUser.getId());
        safeUser.setUsername(originUser.getUsername());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setAvatarUrl(originUser.getAvatarUrl());
        safeUser.setGender(originUser.getGender());
        safeUser.setPhone(originUser.getPhone());
        safeUser.setEmail(originUser.getEmail());
        safeUser.setPlanetCode(originUser.getPlanetCode());
        safeUser.setUserRole(originUser.getUserRole());
        safeUser.setUserStatus(originUser.getUserStatus());
        safeUser.setCreateTime(originUser.getCreateTime());
        safeUser.setTags(originUser.getTags());
        return safeUser;
    }

    /**
     * 用户注销
     * @param request
     */
    @Override
    public int userLogOut(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList 用户要拥有的标签
     * @return
     */
    @Override
    public List<User> searchUsersByTags(List<String> tagNameList){
        if (CollectionUtils.isEmpty(tagNameList)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 拼接 and 查询
        for (String tagName : tagNameList) {
            queryWrapper.like("tags",tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream().map(this::getSafeUser).collect(Collectors.toList());
    }
}




