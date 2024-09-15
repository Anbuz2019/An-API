package com.anbuz.anapibackend.service.impl;

import com.anbuz.anapibackend.model.entity.UserInterfaceInvoke;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anbuz.anapibackend.service.UserInterfaceInvokeService;
import com.anbuz.anapibackend.mapper.UserInterfaceInvokeMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
* @author anbuz
* @description 针对表【user_interface_invoke(用户接口调用表)】的数据库操作Service实现
* @createDate 2024-09-15 23:20:06
*/
@Service
@DubboService
public class UserInterfaceInvokeServiceImpl extends ServiceImpl<UserInterfaceInvokeMapper, UserInterfaceInvoke>
    implements UserInterfaceInvokeService{

    @Override
    public boolean invoke(Long userId, Long interfaceId) {
        if (userId == null || interfaceId == null) {
            return false;
        }
        QueryWrapper<UserInterfaceInvoke> wrapper = new QueryWrapper<>();
        wrapper.eq("userId", userId);
        wrapper.eq("interfaceId", interfaceId);
        UserInterfaceInvoke userInterfaceInvoke = this.getOne(wrapper);
        if (userInterfaceInvoke == null) {
            UserInterfaceInvoke saveOne = new UserInterfaceInvoke();
            saveOne.setUserId(userId);
            saveOne.setInterfaceId(interfaceId);
            saveOne.setTotalInvokes(1L);
            return this.save(saveOne);
        } else{
            if (userInterfaceInvoke.getStatus() == 0) {
                Long totalInvokes = userInterfaceInvoke.getTotalInvokes() + 1;
                userInterfaceInvoke.setTotalInvokes(totalInvokes);
                return this.updateById(userInterfaceInvoke);
            }
        }
        return false;
    }
}




