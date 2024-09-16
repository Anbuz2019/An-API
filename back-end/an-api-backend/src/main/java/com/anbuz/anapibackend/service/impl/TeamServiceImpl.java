package com.anbuz.anapibackend.service.impl;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.constant.RedissonLockConstant;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapicommon.common.ErrorCode;
import com.anbuz.anapibackend.mapper.TeamMapper;
import com.anbuz.anapicommon.model.dto.*;
import com.anbuz.anapicommon.model.entity.Team;
import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapicommon.model.entity.UserTeam;
import com.anbuz.anapicommon.model.enums.TeamStatusEnum;
import com.anbuz.anapicommon.model.vo.TeamUserVO;
import com.anbuz.anapibackend.service.TeamService;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapibackend.service.UserTeamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author anbuz
 * @description 针对表【team(队伍)】的数据库操作Service实现
 * @createDate 2024-09-06 21:32:29
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team>
        implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Resource
    private UserTeamService userTeamService;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 条件查询（全部）
     */
    @Override
    public List<TeamUserVO> search(TeamQueryDTO teamQueryDTO) {
        // 组合查询条件
        Team queryTeam = new Team();
        BeanUtils.copyProperties(teamQueryDTO, queryTeam);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
        String searchText = teamQueryDTO.getSearchText();
        // 支持对 “名称” 和 “描述信息” 的模糊查询
        if (StringUtils.isNotBlank(teamQueryDTO.getSearchText())) {
            queryWrapper.and(qw -> qw.like("name", searchText).or().like("description", searchText));
        }
        // 查询id
        if (teamQueryDTO.getId() != null && teamQueryDTO.getId() > 0) {
            queryWrapper.eq("t.id", teamQueryDTO.getId());
        }
        // 查询 id 列表
        if (teamQueryDTO.getIdList() != null && !teamQueryDTO.getIdList().isEmpty()) {
            queryWrapper.in("t.id", teamQueryDTO.getIdList());
        }
        // 查询用户id
        if (teamQueryDTO.getUserId() != null && teamQueryDTO.getUserId() > 0) {
            queryWrapper.eq("userId", teamQueryDTO.getUserId());
        }
        // 查询最大人数
        if (teamQueryDTO.getMaxNum() != null && teamQueryDTO.getMaxNum() > 0) {
            queryWrapper.eq("maxNum", teamQueryDTO.getMaxNum());
        }
        // 只有管理员可以查看加密队伍
        TeamStatusEnum status = TeamStatusEnum.getEnumByValue(teamQueryDTO.getStatus());
        // 如果不符合枚举值，默认为查询公开的Team
        if (status == null) {
            status = TeamStatusEnum.PUBLIC;
        }
        // 如果不是管理员，不允许访问私密Team
        User loginUser = BaseContext.getCurrentUser();
        if (!userService.isAdmin(loginUser) && status == TeamStatusEnum.PRIVATE) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        queryWrapper.eq("status", status.getValue());
        // 不展示已经过期的队伍
        // expireTime is null or expireTime > now()
        queryWrapper.and(qw -> qw.gt("expireTime", new Date()).or().isNull("expireTime"));
        // 补充：验证是否逻辑删除
        queryWrapper.eq("t.isDelete", 0);
        queryWrapper.eq("u.isDelete", 0);
        List<TeamUserVO> teamUserVOList = teamMapper.listByCondition(queryWrapper);
        // 填充 hasJoin、hasJoinNum 属性
        for (TeamUserVO teamUserVO : teamUserVOList) {
            // 1, 先获取加入当前 team 的 id 的集合
            Long loginId = BaseContext.getCurrentUser().getId();
            Long teamId = teamUserVO.getId();
            QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
            userTeamQueryWrapper.eq("teamId", teamId);
            List<Long> userIdList = userTeamService.list(userTeamQueryWrapper)
                    .stream().map(UserTeam::getUserId).collect(Collectors.toList());
            // 2. 填充 hasJoinNum 属性
            Integer hasJoinNum = userIdList.size();
            teamUserVO.setHasJoinNum(hasJoinNum);
            // 2. 判断是否加入，填充 hasJoin 属性
            Boolean hasJoin = userIdList.contains(loginId);
            teamUserVO.setHasJoin(hasJoin);
        }
        return teamUserVOList;
    }

    /**
     * 条件查询（分页）
     */
    @Override
    public List<Team> searchPage(Integer pageSize, Integer pageNum, TeamQueryDTO teamQueryDTO) {
        Team queryTeam = new Team();
        BeanUtils.copyProperties(teamQueryDTO, queryTeam);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(queryTeam);
        Page<Team> page = teamMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return page.getRecords();
    }

    /**
     * 创建队伍
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveTeam(TeamAddDTO teamAddDTO) {
        // 1. 判空
        if (teamAddDTO == null) throw new BusinessException(ErrorCode.PARAM_ERROR);
        Team team = new Team();
        BeanUtils.copyProperties(teamAddDTO, team);
        // 2. 校验信息
        // （1）队伍人数 > 1 且 < 20
        int maxNum = Optional.ofNullable(team.getMaxNum()).orElse(0);
        if (maxNum < 1 || maxNum > 100) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "队伍人数不满足要求");
        }
        // （2）队伍标题 <= 20 字
        String name = Optional.ofNullable(team.getName()).orElse("");
        if (StringUtils.isBlank(name) || name.length() > 20) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "队伍标题不符合要求");
        }
        // （3）描述 <= 512 字
        String description = Optional.ofNullable(team.getDescription()).orElse("");
        if (StringUtils.isNotBlank(description) && description.length() > 512) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "队伍描述过长");
        }
        // （4）检查status，默认为不公开（0）
        int status = Optional.ofNullable(team.getStatus()).orElse(0);
        TeamStatusEnum teamStatusEnum = TeamStatusEnum.getEnumByValue(status);
        if (teamStatusEnum == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "队伍状态不满足要求");
        }
        team.setStatus(teamStatusEnum.getValue());
        // （5）如果处于加密状态，必须有密码，且 密码 <= 32
        String password = team.getPassword();
        if (TeamStatusEnum.SECRET.equals(teamStatusEnum) && (password == null || password.length() > 32)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码不满足要求");
        }
        // （6）超时时间 > 当前时间
        if (team.getExpireTime() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "过期时间不能为空");
        }
        Date expireTime = team.getExpireTime();
        if (new Date().after(expireTime)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "超时时间必须大于当前时间");
        }
        // 加分布式锁：
        Long userId = BaseContext.getCurrentUser().getId();
        RLock lock = redissonClient.getLock(RedissonLockConstant.TeamCreateUserPrefix + userId);
        int countTry = 0;
        try {
            while (true) {
                countTry++;
                if (countTry > 5) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                boolean getLock = lock.tryLock();
                if (getLock) {
                    // （7）校验用户最多创建 5 个队伍
                    QueryWrapper<Team> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("userId", BaseContext.getCurrentUser().getId());
                    long hasTeamNum = this.count(queryWrapper);
                    if (hasTeamNum >= 5) {
                        throw new BusinessException(ErrorCode.PARAM_ERROR, "当前用户已经创建5个队伍");
                    }
                    // 3. 插入队伍信息到队伍表
                    team.setId(null);
                    team.setUserId(BaseContext.getCurrentUser().getId());
                    boolean save = this.save(team);
                    if (!save || team.getId() == null) {
                        throw new BusinessException(ErrorCode.PARAM_ERROR, "创建队伍失败");
                    }
                    // 4. 插入用户-队伍关系到关系表
                    UserTeam userTeam = new UserTeam();
                    userTeam.setUserId(BaseContext.getCurrentUser().getId());
                    userTeam.setTeamId(team.getId());
                    userTeam.setJoinTime(new Date());
                    boolean saved = userTeamService.save(userTeam);
                    if (!saved) {
                        throw new BusinessException(ErrorCode.PARAM_ERROR, "创建队伍失败");
                    }
                    return team.getId();
                }
            }
        } finally {
            // 释放锁，只能释放自己的锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 更新队伍
     */
    @Override
    public boolean updateTeam(TeamUpdateDTO teamUpdateDTO) {
        if (teamUpdateDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Long id = teamUpdateDTO.getId();
        if (id == null || id < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Team oldTeam = this.getById(id);
        if (oldTeam == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 只有管理员以及队伍创建者可以修改
        User loginUser = BaseContext.getCurrentUser();
        if (!Objects.equals(oldTeam.getUserId(), loginUser.getId())
                && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        // 如果状态改为加密，必须携带密码
        TeamStatusEnum statusEnum = TeamStatusEnum.getEnumByValue(teamUpdateDTO.getStatus());
        if (statusEnum.equals(TeamStatusEnum.SECRET)) {
            if (StringUtils.isBlank(teamUpdateDTO.getPassword())) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "加密房价必须设置密码");
            }
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamUpdateDTO, team);
        return this.updateById(team);
    }

    /**
     * 加入队伍
     */
    @Override
    public Boolean joinTeam(TeamJoinDTO teamJoinDTO) {
        if (teamJoinDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Long teamId = teamJoinDTO.getTeamId();
        Team team = getTeamById(teamId);
        if (team.getExpireTime() != null && team.getExpireTime().before(new Date())) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍已过期");
        }
        // 禁止加入个人队伍
        Integer status = team.getStatus();
        TeamStatusEnum teamStatusEnum = TeamStatusEnum.getEnumByValue(status);
        if (TeamStatusEnum.PRIVATE.equals(teamStatusEnum)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 加入加密队伍须提供正确密码
        if (TeamStatusEnum.SECRET.equals(teamStatusEnum)) {
            String password = teamJoinDTO.getPassword();
            if (StringUtils.isBlank(password) || password.equals(team.getPassword())) {
                throw new BusinessException(ErrorCode.PARAM_ERROR, "密码错误");
            }
        }

        // 分布式锁，这里同时加两个锁
        Long userId = BaseContext.getCurrentUser().getId();
        RLock userLock = redissonClient.getLock(RedissonLockConstant.TeamJoinUserPrefix + userId);
        RLock teamLock = redissonClient.getLock(RedissonLockConstant.TeamJoinTeamPrefix + teamId);
        RLock multiLock = redissonClient.getMultiLock(userLock, teamLock);

        // 加锁，防止多线程的请求重复
        int countTry = 0;
        try {
            while (true) {
                countTry++;
                if (countTry > 5) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                boolean getLock = multiLock.tryLock(1, -1, TimeUnit.SECONDS);
                if (getLock) {
                    // 一个人只能最多加入5个队伍
                    System.out.println(Thread.currentThread().getId() + "获取锁");
                    User loginUser = BaseContext.getCurrentUser();
                    QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("userId", loginUser.getId());
                    long count = userTeamService.count(queryWrapper);
                    if (count >= 5) {
                        throw new BusinessException(ErrorCode.PARAM_ERROR, "最多创建和加入5个队伍");
                    }
                    // 不能重复加入同一队伍
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("userId", loginUser.getId());
                    queryWrapper.eq("teamId", teamId);
                    long countUserTeam = userTeamService.count(queryWrapper);
                    if (countUserTeam > 0) {
                        throw new BusinessException(ErrorCode.PARAM_ERROR, "不能重复加入同一队伍");
                    }
                    // 只能加入未满的队伍
                    long teamHasCount = getCountTeam(teamId);
                    if (teamHasCount >= team.getMaxNum()) {
                        throw new BusinessException(ErrorCode.NULL_ERROR, "队伍已满");
                    }
                    // 修改队伍-用户信息
                    UserTeam userTeam = new UserTeam();
                    userTeam.setUserId(loginUser.getId());
                    userTeam.setTeamId(teamId);
                    userTeam.setJoinTime(new Date());
                    userTeam.setUpdateTime(new Date());
                    return userTeamService.save(userTeam);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (multiLock.isHeldByCurrentThread()) {
                multiLock.unlock();
                System.out.println(Thread.currentThread().getId() + "释放锁");
            }
        }
    }

    /**
     * 获取加入某队伍的总人数
     *
     * @param teamId 队伍id
     * @return 总人数
     */
    private long getCountTeam(Long teamId) {
        QueryWrapper<UserTeam> queryWrapper;
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        return userTeamService.count(queryWrapper);
    }

    /**
     * 退出队伍
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean quitTeam(TeamQuitDTO teamQuitDTO) {
        if (teamQuitDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Long teamId = teamQuitDTO.getTeamId();
        Team team = getTeamById(teamId);
        long userId = BaseContext.getCurrentUser().getId();
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("teamId", teamId);
        long inTeam = userTeamService.count(queryWrapper);
        if (inTeam == 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "不在该队伍中");
        }
        long teamHasCount = getCountTeam(teamId);
        // 队伍无人，直接解散
        if (teamHasCount == 1) {
            // 删除队伍
            this.removeById(teamId);
        } else {
            if (team.getUserId() == userId) {
                // 把队伍转移给最早加入的用户
                // 1. 查询所有用户加入的时间
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("teamId", teamId);
                queryWrapper.last("order by id asc limit 2");
                List<UserTeam> userTeamList = userTeamService.list(queryWrapper);
                if (CollectionUtils.isEmpty(userTeamList) || userTeamList.size() == 1) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
                UserTeam nextUserTeam = userTeamList.get(1);
                Long nextUserTeamUserId = nextUserTeam.getUserId();
                // 更新当前队伍的队长
                Team updateTeam = new Team();
                updateTeam.setUserId(nextUserTeamUserId);
                updateTeam.setUpdateTime(new Date());
                boolean update = this.updateById(updateTeam);
                if (!update) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新队长失败");
                }
            }
        }
        // 移除当前用户加入队伍的关系
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        queryWrapper.eq("userId", userId);
        return userTeamService.remove(queryWrapper);
    }

    /**
     * 根据id获取Team实体
     */
    private Team getTeamById(Long teamId) {
        if (teamId == null || teamId <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Team team = this.getById(teamId);
        if (team == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "队伍不存在");
        }
        return team;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTeam(Long teamId) {
        if (teamId == null || teamId <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 校验team是否存在
        Team team = getTeamById(teamId);
        // 校验是否是队长
        long loginId = BaseContext.getCurrentUser().getId();
        if (loginId != team.getUserId()) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        // 移除所有队伍-用户的关联信息
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teamId", teamId);
        queryWrapper.eq("userId", loginId);
        boolean remove = userTeamService.remove(queryWrapper);
        if (!remove) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除关联信息失败");
        }
        // 删除队伍
        return this.removeById(teamId);
    }

    /**
     * 搜索加入的队伍
     */
    @Override
    public List<TeamUserVO> searchJoinTeam(TeamQueryDTO teamQueryDTO) {
        // 获取用户的user-team关系
        Long LoginId = BaseContext.getCurrentUser().getId();
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", LoginId);
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);
        // 进行过滤（去重）
        Map<Long, List<UserTeam>> listMap = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
        List<Long> teamIdList = new ArrayList<>(listMap.keySet());
        teamQueryDTO.setIdList(teamIdList);
        return this.search(teamQueryDTO);
    }
}




