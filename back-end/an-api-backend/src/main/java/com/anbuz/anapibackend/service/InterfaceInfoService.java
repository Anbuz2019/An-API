package com.anbuz.anapibackend.service;

import com.anbuz.anapicommon.model.dto.InterfaceInfoOnlineDTO;
import com.anbuz.anapicommon.model.dto.InterfaceInfoQueryDTO;
import com.anbuz.anapicommon.model.entity.InterfaceInfo;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


/**
* @author anbuz
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-09-11 17:11:26
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryDTO interfaceInfoQueryDTO);

    InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo);

    Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage);

    Boolean onlineInterfaceInfo(InterfaceInfoOnlineDTO interfaceInfoOnlineDTO);

    Boolean offlineInterfaceInfo(InterfaceInfoOnlineDTO interfaceInfoOnlineDTO);
}
