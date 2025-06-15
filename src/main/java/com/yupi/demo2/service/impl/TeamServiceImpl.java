package com.yupi.demo2.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.demo2.mapper.TeamMapper;
import com.yupi.demo2.model.Team;
import com.yupi.demo2.service.TeamService;
import org.springframework.stereotype.Service;


/**
* @author 16179
* @description 针对表【team(队伍)】的数据库操作Service实现
* @createDate 2025-06-15 20:27:37
*/
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
    implements TeamService {

}




