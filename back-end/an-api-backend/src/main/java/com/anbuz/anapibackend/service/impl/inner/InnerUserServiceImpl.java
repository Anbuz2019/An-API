package com.anbuz.anapibackend.service.impl.inner;

import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import com.anbuz.anapibackend.mapper.UserMapper;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapicommon.service.inner.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class InnerUserServiceImpl extends ServiceImpl<UserMapper, User>
        implements InnerUserService {

    /**
     * 根据 accessKey 获取用户信息（未脱敏，不可公开）
     */
    @Override
    public User getUserByAccessKey(String accessKey) {
        if (StringUtils.isBlank(accessKey)){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey", accessKey);
        return this.getOne(queryWrapper);
    }

}
