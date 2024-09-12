package com.anbuz.anapibackend.service.impl;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.constant.UserConstant;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import com.anbuz.anapibackend.mapper.UserMapper;
import com.anbuz.anapibackend.model.dto.UserLoginDTO;
import com.anbuz.anapibackend.model.dto.UserRegisterDTO;
import com.anbuz.anapibackend.model.entity.User;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapibackend.utils.TagsSimilarityUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    /**
     * 盐值，用于混淆密码
     */
    private static final String SALT = "anbuz";

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Long userRegister(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String userAccount = userRegisterDTO.getUserAccount();
        String passwd = userRegisterDTO.getPasswd();
        String checkPasswd = userRegisterDTO.getCheckPasswd();
        // 1. 校验：不为空
        if (StringUtils.isAnyBlank(userAccount, passwd, checkPasswd, username)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "参数为空");
        }
        // 校验：账号不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号不应小于4位");
        }
        // 校验：密码不小于8位
        if (passwd.length() < 8 || checkPasswd.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码不应小于8位");
        }
        // 校验账户不包含特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;,\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户不应包含特殊字符");
        }
        // 密码和校验密码相同
        if (!passwd.equals(checkPasswd)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码和校验密码须一致");
        }
        // 校验：账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.CONFLICT_ERROR, "该账号已存在");
        }
        // 2. 对代码进行加密
        String encryptPasswd = DigestUtils.md5DigestAsHex((SALT + passwd).getBytes());

        // 3. 向数据库插入该数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPasswd);
        user.setUsername(username);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String userAccount = userLoginDTO.getUserAccount();
        String passwd = userLoginDTO.getUserPassword();
        // 1. 校验：不为空
        if (StringUtils.isAnyBlank(userAccount, passwd)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "账号或密码不能为空");
        }
        // 校验：账号不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号不能小于4位");
        }
        // 校验：密码不小于8位
        if (passwd.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码不能小于8位");
        }
        // 校验账户不包含特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;,\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户不应包含特殊字符");
        }

        // 2. 对代码进行加密
        String encryptPasswd = DigestUtils.md5DigestAsHex((SALT + passwd).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPasswd);
        User user = this.getOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, user account:{} cannot match passwd", userAccount);
            throw new BusinessException(ErrorCode.CONFLICT_ERROR, "账号名或密码错误");
        }

        // 3. 用户脱敏（去除密码等敏感信息）
        User safetyUser = getSaftyUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    @Override
    public List<User> searchUsers(String username, HttpServletRequest request) {
        // 校验权限
        if (!isAdmin(request)) throw new BusinessException(ErrorCode.NOT_AUTH, "只有管理员可以操作");
        // 查询用户表
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isAnyBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> list = this.list(queryWrapper);
        return list.stream().map(this::getSaftyUser).collect(Collectors.toList());
    }

    @Override
    public List<User> recommendUsers(Integer pageSize, Integer pageNum) {
        // redis缓存命名
        String redisKey = "anapibackend:user:recommendUsers:" + BaseContext.getCurrentUser().getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 查找缓存，如果有直接读取
        List<User> userList = (List<User>) valueOperations.get(redisKey);
        if (userList != null) return userList;
        // 无缓存，查询 MYSQL 用户表
        userList = this.matchUser(pageSize);
        // 将查到的内容记录在缓存中（过期时间为600秒）
        try {
            valueOperations.set(redisKey, userList, 600000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            log.error("Redis set key error: {}", e.getMessage());
        }
        return userList;
    }

    @Override
    public boolean remove(Long id, HttpServletRequest request) {
        // 校验权限
        if (!isAdmin(request)) throw new BusinessException(ErrorCode.NOT_AUTH, "只有管理员可以操作");
        return this.removeById(id);
    }

    /**
     * 是否为管理员
     *
     * @param request Http请求
     * @return 是否为管理员
     */
    public boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        log.info("校验用户信息：{}", user);
        return user != null && !Objects.equals(user.getUserRole(), UserConstant.DEFAULT_ROLE);
    }

    public boolean isAdmin(User user) {
        return user != null && !Objects.equals(user.getUserRole(), UserConstant.DEFAULT_ROLE);
    }

    @Override
    public List<User> matchUser(Integer num) {
        if (num <= 0 || num > 20) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 查询数据库中的所有带标签用户（排除自己）
        User loginUser = BaseContext.getCurrentUser();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("id", loginUser.getId());
        queryWrapper.isNotNull("tags");
        queryWrapper.select("id", "tags");
        List<User> userList = this.list(queryWrapper);
        // 查询当前用户，如果没有tags先报错
        User currentUser = this.getById(loginUser.getId());
        if (currentUser == null || currentUser.getTags() == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 定义优先队列（小顶堆），初始容量为 num
        Gson gson = new Gson();
        TypeToken<List<String>> token = new TypeToken<List<String>>() {
        };
        List<String> tagList = gson.fromJson(currentUser.getTags(), token);
        Comparator<Integer> userIndexComparator = (a, b) -> {
            List<String> tagListA = gson.fromJson(userList.get(a).getTags(), token);
            List<String> tagListB = gson.fromJson(userList.get(b).getTags(), token);
            Integer scoreA = TagsSimilarityUtil.TagCoOccurrence1(tagList, tagListA);
            Integer scoreB = TagsSimilarityUtil.TagCoOccurrence1(tagList, tagListB);
            return Integer.compare(scoreA, scoreB);
        };
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(num, userIndexComparator);
        // 遍历 userList，维护前 num 个最大值的索引
        for (Integer i = 0; i < userList.size(); i++) {
            if (minHeap.size() < num) {
                minHeap.offer(i);  // 直接插入索引
            } else if (userIndexComparator.compare(i, minHeap.peek()) > 0) {
                minHeap.poll();  // 移除堆顶较小的元素索引
                minHeap.offer(i);  // 插入新的较大元素索引
            }
        }
        // 取出 minHeap 所有下标对应的 id
        // 由于内部结构是基于堆的性质维护的，
        // 而调用 toArray() 方法只是将堆中的元素以层序遍历的方式返回到一个数组中，
        // 因此如果直接使用 toArray，数组元素的顺序不会保持堆中按优先级排列的顺序。
        List<Long> idList = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            idList.add(userList.get(minHeap.poll()).getId());  // 逐个弹出最小值
        }
        Collections.reverse(idList);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.in("id", idList);
        // 使用 FIELD() 函数按照 idList 的顺序排序
        String fieldOrder = idList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        userQueryWrapper.last("ORDER BY FIELD(id, " + fieldOrder + ")");

        // 对结果脱敏
        List<User> list = this.list(userQueryWrapper);
        list = list.stream().map(this::getSaftyUser).collect(Collectors.toList());
        return list;
    }

    /**
     * 用户脱敏
     *
     * @param originUser 未处理的用户实体
     * @return 脱敏后的用户实体
     */
    @Override
    public User getSaftyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setTags(originUser.getTags());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        return safetyUser;
    }

    /**
     * 用户退出登录
     *
     * @param request Http请求
     */
    @Override
    public Integer userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
        return 0;
    }

    /**
     * 根据标签搜索用户
     */
    @Override
    public List<User> searchByTags(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "标签至少要有一个");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for (String tagName : tagNameList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        return userList.stream().map(this::getSaftyUser).collect(Collectors.toList());
    }

    /**
     * 修改用户信息
     *
     * @param user 需要改成的用户信息
     * @return code
     */
    @Override
    public Integer updateUser(User user) {
        User currentUser = BaseContext.getCurrentUser();
        // 1. 仅管理员和自己可修改
        if (!Objects.equals(currentUser.getId(), user.getId()) && !isAdmin(currentUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        // 2. 查询用户是否存在
        User oldUser = userMapper.selectById(user.getId());
        if (oldUser == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 3. 更新数据
        return userMapper.updateById(user);
    }
}

