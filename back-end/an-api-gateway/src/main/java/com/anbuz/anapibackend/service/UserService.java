package com.anbuz.anapibackend.service;

import com.anbuz.anapibackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author anbuz
 */
public interface UserService extends IService<User> {
    /**
     * 根据 accessKey 获取用户信息（未脱敏，不可公开）
     */
    User getUserByAccessKey(String accessKey);
}
