package com.anbuz.anapibackend.service;

import com.anbuz.anapibackend.model.dto.UserLoginDTO;
import com.anbuz.anapibackend.model.dto.UserRegisterDTO;
import com.anbuz.anapibackend.model.entity.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author anbuz
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userRegisterDTO 用户注册DTO
     * @return 新用户的id
     */
    Long userRegister(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息DTO
     * @return 用户的信息（脱敏）
     */
    User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request);

    /**
     * 用户搜索（根据用户名）
     * @param username 用户名
     * @return 用户列表
     */
    List<User> searchUsers(String username, HttpServletRequest request);

    /**
     * 用户推荐页
     */
    List<User> recommendUsers(Integer pageSize, Integer pageNum);

    /**
     * 根据id删除用户
     * @param id 用户id
     * @return 操作是否成功
     */
    boolean remove(Long id, HttpServletRequest request);

    /**
     * 用户脱敏
     */
    User getSaftyUser(User user);

    /**
     * 用户注销
     */
    Integer userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     */
    List<User> searchByTags(List<String> tagNameList);

    /**
     * 用户信息修改
     */
    Integer updateUser(User user);

    /**
     * 检查是否为管理员
     */
    boolean isAdmin(User user);

    /**
     * 匹配用户
     */
    List<User> matchUser(Integer num);
}
