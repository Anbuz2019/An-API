package com.anbuz.anapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anbuz.anapibackend.model.entity.UserTeam;
import com.anbuz.anapibackend.service.UserTeamService;
import com.anbuz.anapibackend.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author anbuz
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-09-06 21:36:24
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




