package com.anbuz.anapibackend.service.impl.inner;

import com.anbuz.anapibackend.mapper.UserInterfaceInvokeMapper;
import com.anbuz.anapicommon.model.entity.UserInterfaceInvoke;
import com.anbuz.anapicommon.service.inner.InnerUserInterfaceInvokeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class InnerUserInterfaceInvokeServiceImpl extends ServiceImpl<UserInterfaceInvokeMapper, UserInterfaceInvoke>
        implements InnerUserInterfaceInvokeService {

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