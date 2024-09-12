package com.anbuz.anapibackend.mapper;

import com.anbuz.anapibackend.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author anbuz
* @description 针对表【interface_info(接口信息)】的数据库操作Mapper
* @createDate 2024-09-11 17:11:26
* @Entity com.anbuz.anapibackend.model.entity.InterfaceInfo
*/

@Mapper
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

}




