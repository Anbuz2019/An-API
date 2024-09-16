package com.anbuz.anapicommon.service.inner;

import com.anbuz.anapicommon.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author anbuz
 */
public interface InnerUserService extends IService<User> {
    /**
     * 根据 accessKey 获取用户信息（未脱敏，不可公开）
     */
    User getUserByAccessKey(String accessKey);
}
