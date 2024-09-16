package com.anbuz.anapibackend.service;

import com.anbuz.anapicommon.model.dto.*;
import com.anbuz.anapicommon.model.entity.Team;
import com.anbuz.anapicommon.model.vo.TeamUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author anbuz
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-09-06 21:32:29
*/
public interface TeamService extends IService<Team> {

    /**
     * 条件查询（全部）
     */
    List<TeamUserVO> search(TeamQueryDTO teamQueryDTO);

    /**
     * 条件查询（分页）
     */
    List<Team> searchPage(Integer pageSize, Integer pageNum, TeamQueryDTO teamQueryDTO);

    /**
     * 添加队伍
     */
    Long saveTeam(TeamAddDTO team);

    /**
     * 更新队伍
     */
    boolean updateTeam(TeamUpdateDTO teamUpdateDTO);

    /**
     * 加入队伍
     */
    Boolean joinTeam(TeamJoinDTO teamJoinDTO);

    /**
     * 退出队伍
     */
    Boolean quitTeam(TeamQuitDTO teamQuitDTO);

    /**
     * 解散队伍
     */
    boolean deleteTeam(Long teamId);

    /**
     * 搜索加入的队伍
     */
    List<TeamUserVO> searchJoinTeam(TeamQueryDTO teamQueryDTO);
}
