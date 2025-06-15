package com.yupi.demo2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yupi.demo2.mapper.UserTeamMapper;
import com.yupi.demo2.model.UserTeam;
import com.yupi.demo2.service.UserTeamService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 16179
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2025-06-15 20:29:35
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




