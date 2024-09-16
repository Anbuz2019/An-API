package com.anbuz.anapibackend.service.impl.inner;

import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import com.anbuz.anapibackend.mapper.InterfaceInfoMapper;
import com.anbuz.anapicommon.model.entity.InterfaceInfo;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.anbuz.anapicommon.service.inner.InnerInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@DubboService
public class InnerInterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InnerInterfaceInfoService {
    @Override
    public InterfaceInfoVO getInterfaceByMethodAndURI(String method, String url) {
        if (StringUtils.isEmpty(method) || StringUtils.isEmpty(url)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("method", method);
        queryWrapper.eq("url", url);
        InterfaceInfo info = this.getOne(queryWrapper);
        if (info == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(info, interfaceInfoVO);
        return interfaceInfoVO;
    }
}
