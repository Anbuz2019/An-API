package com.anbuz.anapibackend.service.impl;

import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.service.UserService;
import com.anbuz.anapicommon.common.ErrorCode;
import com.anbuz.anapibackend.exception.ThrowUtils;
import com.anbuz.anapibackend.mapper.InterfaceInfoMapper;
import com.anbuz.anapibackend.service.InterfaceInfoService;
import com.anbuz.anapicommon.model.dto.InterfaceInfoOnlineDTO;
import com.anbuz.anapicommon.model.dto.InterfaceInfoQueryDTO;
import com.anbuz.anapicommon.model.entity.InterfaceInfo;
import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author anbuz
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2024-09-11 17:11:26
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Resource
    UserService userService;

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        String name = interfaceInfo.getName();
        String method = interfaceInfo.getMethod();
        String url = interfaceInfo.getUrl();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, method, url), ErrorCode.PARAM_ERROR);
        }
        // 有参数则校验
        if (!StringUtils.isNotBlank(name) || name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "接口名称不能为空或过长");
        }
        if (StringUtils.isNotBlank(url) && url.length() > 8192) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "url过长");
        }
    }

    /**
     * 获取查询包装类
     *
     * @param interfaceInfoQueryDTO
     * @return
     */
    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryDTO interfaceInfoQueryDTO) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        if (interfaceInfoQueryDTO == null) {
            return queryWrapper;
        }
        Long id = interfaceInfoQueryDTO.getId();
        String name = interfaceInfoQueryDTO.getName();
        String method = interfaceInfoQueryDTO.getMethod();
        String url = interfaceInfoQueryDTO.getUrl();
        Long userId = interfaceInfoQueryDTO.getUserId();
        Integer interfaceStatus = interfaceInfoQueryDTO.getInterfaceStatus();
        String searchText = interfaceInfoQueryDTO.getSearchText();
        String sortField = interfaceInfoQueryDTO.getSortField();
        String sortOrder = interfaceInfoQueryDTO.getSortOrder();
        // 拼接查询条件
        if (StringUtils.isNotBlank(searchText)) {
            queryWrapper.and(qw -> qw.like("name", searchText).or().
                    like("interfaceDescription", searchText));
        }
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.like(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.ne(ObjectUtils.isNotEmpty(interfaceStatus), "interfaceStatus", interfaceStatus);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
//        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
//                sortField);
        return queryWrapper;
    }

    @Override
    public InterfaceInfoVO getInterfaceInfoVO(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo info = this.getById(interfaceInfo.getId());
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(info, interfaceInfoVO);
        return interfaceInfoVO;
    }

    @Override
    public Page<InterfaceInfoVO> getInterfaceInfoVOPage(Page<InterfaceInfo> interfaceInfoPage) {
        // 创建一个新的 Page 对象，用于存储 VO 数据
        Page<InterfaceInfoVO> voPage = new Page<>(interfaceInfoPage.getCurrent(),
                interfaceInfoPage.getSize(), interfaceInfoPage.getTotal());

        // 获取分页查询结果中的记录（InterfaceInfo 实体列表）
        List<InterfaceInfo> interfaceInfoList = interfaceInfoPage.getRecords();

        // 将 InterfaceInfo 转换为 InterfaceInfoVO
        List<InterfaceInfoVO> voList = interfaceInfoList.stream()
                .map(this::toVO)
                .collect(Collectors.toList());

        // 将转换后的 VO 列表设置到 Page 对象中
        voPage.setRecords(voList);

        return voPage;
    }

    @Override
    public Boolean onlineInterfaceInfo(InterfaceInfoOnlineDTO interfaceInfoOnlineDTO) {
        if (interfaceInfoOnlineDTO==null || interfaceInfoOnlineDTO.getId()<0){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo info = this.getById(interfaceInfoOnlineDTO.getId());
        if (info == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long userId = BaseContext.getCurrentUser().getId();
        User user = userService.getById(userId);
        // 只有管理员以及创建者可以发布
        if (!userService.isAdmin(user) && !Objects.equals(info.getUserId(), userId)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(info.getId());
        interfaceInfo.setInterfaceStatus(1);
        return this.updateById(interfaceInfo);
    }

    @Override
    public Boolean offlineInterfaceInfo(InterfaceInfoOnlineDTO interfaceInfoOnlineDTO) {
        if (interfaceInfoOnlineDTO==null || interfaceInfoOnlineDTO.getId()<0){
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo info = this.getById(interfaceInfoOnlineDTO.getId());
        if (info == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        Long userId = BaseContext.getCurrentUser().getId();
        User user = userService.getById(userId);
        // 只有管理员以及创建者可以下线
        if (!userService.isAdmin(user) && !Objects.equals(info.getUserId(), userId)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        interfaceInfo.setId(info.getId());
        interfaceInfo.setInterfaceStatus(0);
        return this.updateById(interfaceInfo);
    }


    private InterfaceInfoVO toVO(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            return null;
        }

        InterfaceInfoVO vo = new InterfaceInfoVO();
        vo.setId(interfaceInfo.getId());
        vo.setName(interfaceInfo.getName());
        vo.setMethod(interfaceInfo.getMethod());
        vo.setUrl(interfaceInfo.getUrl());
        vo.setUserId(interfaceInfo.getUserId());
        vo.setRequestHeader(interfaceInfo.getRequestHeader());
        vo.setResponseHeader(interfaceInfo.getResponseHeader());
        vo.setInterfaceDescription(interfaceInfo.getInterfaceDescription());
        vo.setInterfaceStatus(interfaceInfo.getInterfaceStatus());
        vo.setCreateTime(interfaceInfo.getCreateTime());
        vo.setUpdateTime(interfaceInfo.getUpdateTime());

        return vo;
    }
}




