package com.anbuz.anapibackend.mapper;

import com.anbuz.anapibackend.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author anbuz
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2024-08-19 16:30:53
* @Entity com.anbuz.anapibackend.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




