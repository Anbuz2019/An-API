package com.anbuz.anapicommon.service.inner;


import com.anbuz.anapicommon.model.entity.InterfaceInfo;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author anbuz
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-09-11 17:11:26
*/
public interface InnerInterfaceInfoService extends IService<InterfaceInfo> {

    InterfaceInfoVO getInterfaceByMethodAndURI(String method, String uri);
}
