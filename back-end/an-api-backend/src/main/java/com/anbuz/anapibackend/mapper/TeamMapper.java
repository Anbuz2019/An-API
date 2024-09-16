package com.anbuz.anapibackend.mapper;

import com.anbuz.anapicommon.model.entity.Team;
import com.anbuz.anapicommon.model.vo.TeamUserVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author anbuz
* @description 针对表【team(队伍)】的数据库操作Mapper
* @createDate 2024-09-06 21:32:29
* @Entity com.anbuz.anapicommon.model.entity.Team
*/
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

    List<TeamUserVO> listByCondition(@Param(Constants.WRAPPER) Wrapper<Team> Wrapper);
}




