package com.anbuz.anapibackend.controller;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapicommon.common.BaseResponse;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapicommon.common.ErrorCode;
import com.anbuz.anapicommon.model.dto.*;
import com.anbuz.anapicommon.model.entity.Team;
import com.anbuz.anapicommon.model.vo.TeamUserVO;
import com.anbuz.anapibackend.service.TeamService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/team")
@Api(tags = "组队相关接口")
public class TeamController {

    @Resource
    private TeamService teamService;

    @PostMapping("/add")
    public BaseResponse<Long> add(@RequestBody TeamAddDTO teamAddDTO) {
        if (teamAddDTO == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        Long teamId = teamService.saveTeam(teamAddDTO);
        if (teamId == null) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        return BaseResponse.success(teamId);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody TeamQuitDTO teamQuitDTO) {
        Long teamId = teamQuitDTO.getTeamId();
        if (teamId == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        boolean remove = teamService.deleteTeam(teamId);
        if (!remove) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        return BaseResponse.success(true);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> update(@RequestBody TeamUpdateDTO teamUpdateDTO) {
        if (teamUpdateDTO == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        boolean update = teamService.updateTeam(teamUpdateDTO);
        if (!update) throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        return BaseResponse.success(true);
    }

    @GetMapping("/get")
    public BaseResponse<Team> getByTeamId(Long id) {
        if (id == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        Team team = teamService.getById(id);
        if (team == null) throw new BusinessException(ErrorCode.NULL_ERROR);
        return BaseResponse.success(team);
    }

    @GetMapping("/list")
    public BaseResponse<List<TeamUserVO>> list(TeamQueryDTO teamQueryDTO) {
        if (teamQueryDTO == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        List<TeamUserVO> teamList = teamService.search(teamQueryDTO);
        return BaseResponse.success(teamList);
    }

    @GetMapping("/list/page")
    public BaseResponse<List<Team>> listPage(Integer pageSize, Integer pageNum, TeamQueryDTO teamQueryDTO) {
        if (teamQueryDTO == null || pageSize == null || pageNum == null)
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        List<Team> teamList = teamService.searchPage(pageSize, pageNum, teamQueryDTO);
        return BaseResponse.success(teamList);
    }

    @PostMapping("/join")
    public BaseResponse<Boolean> join(@RequestBody TeamJoinDTO teamJoinDTO) {
        if (teamJoinDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Boolean res = teamService.joinTeam(teamJoinDTO);
        return BaseResponse.success(res);
    }

    @PostMapping("/quit")
    public BaseResponse<Boolean> quit(@RequestBody TeamQuitDTO teamQuitDTO) {
        if (teamQuitDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Boolean res = teamService.quitTeam(teamQuitDTO);
        return BaseResponse.success(res);
    }

    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVO>> listMyCreateTeam(TeamQueryDTO teamQueryDTO) {
        if (teamQueryDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        teamQueryDTO.setUserId(BaseContext.getCurrentUser().getId());
        List<TeamUserVO> search = teamService.search(teamQueryDTO);
        return BaseResponse.success(search);
    }

    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVO>> listMyJoinTeam(TeamQueryDTO teamQueryDTO) {
        if (teamQueryDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        List<TeamUserVO> search = teamService.searchJoinTeam(teamQueryDTO);
        return BaseResponse.success(search);
    }
}
