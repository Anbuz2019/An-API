package com.anbuz.anapibackend.mapper;

import com.anbuz.anapibackend.model.entity.UserInterfaceInvoke;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.ManagedBean;

/**
* @author anbuz
* @description 针对表【user_interface_invoke(用户接口调用表)】的数据库操作Mapper
* @createDate 2024-09-15 23:20:06
* @Entity com.anbuz.anapibackend.model.entity.UserInterfaceInvoke
*/
@Mapper
public interface UserInterfaceInvokeMapper extends BaseMapper<UserInterfaceInvoke> {

}




