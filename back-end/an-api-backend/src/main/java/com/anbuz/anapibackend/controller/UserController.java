package com.anbuz.anapibackend.controller;

import com.anbuz.anapicommon.common.BaseResponse;
import com.anbuz.anapibackend.constant.UserConstant;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapicommon.common.ErrorCode;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapicommon.model.dto.UserLoginDTO;
import com.anbuz.anapicommon.model.dto.UserRegisterDTO;
import com.anbuz.anapicommon.model.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    public BaseResponse<Long> register(@RequestBody UserRegisterDTO registerDTO) {
        if (registerDTO == null) throw new BusinessException(ErrorCode.PARAM_ERROR, "请求参数为空");
        return BaseResponse.success(userService.userRegister(registerDTO));
    }

    /**
     * 用户获取个人信息
     */
    @GetMapping("current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long userId = user.getId();
        return BaseResponse.success(userService.getSaftyUser(userService.getById(userId)));
    }

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public BaseResponse<User> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        return BaseResponse.success(userService.userLogin(userLoginDTO, request));
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/search")
    @ApiOperation("根据用户名查询用户")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        return BaseResponse.success(userService.searchUsers(username, request));
    }

    /**
     * 主页用户推荐
     */
    @GetMapping("/recommend")
    @ApiOperation("主页用户推荐")
    public BaseResponse<List<User>> recommendUsers(Integer pageSize, Integer pageNum) {
        return BaseResponse.success(userService.recommendUsers(pageSize, pageNum));
    }

    /**
     * 删除用户（通过id）
     */
    @PostMapping("/delete")
    @ApiOperation("通过id删除用户")
    public BaseResponse<Boolean> deleteUser(Long id, HttpServletRequest request) {
        if (id <= 0) return BaseResponse.success(false);
        return BaseResponse.success(userService.remove(id, request));
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) throw new BusinessException(ErrorCode.NULL_ERROR, "找不到当前用户");
        return BaseResponse.success(userService.userLogout(request));
    }

    /**
     * 根据标签搜索用户
     */
    @ApiOperation("搜索指定标签的用户")
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUsersByTags(@RequestParam(required = false) List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "标签至少要有一个");
        }
        List<User> userList = userService.searchByTags(tagNameList);
        return BaseResponse.success(userList);
    }

    /**
     * 用户信息修改
     */
    @ApiOperation("用户信息修改")
    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody User user) {
        // 校验参数是否为空
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Integer res = userService.updateUser(user);
        return BaseResponse.success(res);
    }

    @GetMapping("/match")
    public BaseResponse<List<User>> matchUsers(@RequestParam(required = false) Integer num) {
        if (num <= 0 || num > 20) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        List<User> userList = userService.matchUser(num);
        return BaseResponse.success(userList);
    }
}
