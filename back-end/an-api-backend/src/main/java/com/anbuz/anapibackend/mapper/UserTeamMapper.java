package com.anbuz.anapibackend.mapper;

import com.anbuz.anapibackend.model.entity.UserTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author anbuz
* @description 针对表【user_team(用户队伍关系)】的数据库操作Mapper
* @createDate 2024-09-06 21:36:24
* @Entity com.anbuz.anapibackend.model.entity.UserTeam
*/
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

}




