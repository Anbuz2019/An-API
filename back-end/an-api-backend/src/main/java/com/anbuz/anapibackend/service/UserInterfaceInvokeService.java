package com.anbuz.anapibackend.service;

import com.anbuz.anapibackend.model.entity.UserInterfaceInvoke;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author anbuz
* @description 针对表【user_interface_invoke(用户接口调用表)】的数据库操作Service
* @createDate 2024-09-15 23:20:06
*/
public interface UserInterfaceInvokeService extends IService<UserInterfaceInvoke> {

    boolean invoke(Long userId, Long interfaceId);

}
