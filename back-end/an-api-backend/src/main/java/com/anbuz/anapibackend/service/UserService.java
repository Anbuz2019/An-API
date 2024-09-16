package com.anbuz.anapibackend.service;

import com.anbuz.anapicommon.model.dto.UserLoginDTO;
import com.anbuz.anapicommon.model.dto.UserRegisterDTO;
import com.anbuz.anapicommon.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author anbuz
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-09-15 20:02:03
 */
public interface UserService extends IService<User> {

    Long userRegister(UserRegisterDTO userRegisterDTO);

    User userLogin(UserLoginDTO userLoginDTO, HttpServletRequest request);

    List<User> searchUsers(String username, HttpServletRequest request);

    User getSaftyUser(User originUser);

    List<User> matchUser(Integer num);

    boolean remove(Long id, HttpServletRequest request);

    List<User> recommendUsers(Integer pageSize, Integer pageNum);

    Integer userLogout(HttpServletRequest request);

    List<User> searchByTags(List<String> tagNameList);

    Integer updateUser(User user);

    boolean isAdmin(User user);
}
