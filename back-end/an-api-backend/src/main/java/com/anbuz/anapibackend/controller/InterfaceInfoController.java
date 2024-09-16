package com.anbuz.anapibackend.controller;

import com.anbuz.anapibackend.annotation.AuthCheck;
import com.anbuz.anapibackend.common.BaseContext;
import com.anbuz.anapibackend.common.BaseResponse;
import com.anbuz.anapibackend.constant.UserConstant;
import com.anbuz.anapibackend.exception.BusinessException;
import com.anbuz.anapibackend.exception.ErrorCode;
import com.anbuz.anapibackend.exception.ThrowUtils;
import com.anbuz.anapicommon.model.dto.DeleteDTO;
import com.anbuz.anapicommon.model.dto.InterfaceInfoAddDTO;
import com.anbuz.anapicommon.model.dto.InterfaceInfoQueryDTO;
import com.anbuz.anapicommon.model.dto.InterfaceInfoUpdateDTO;
import com.anbuz.anapicommon.model.entity.InterfaceInfo;
import com.anbuz.anapicommon.model.entity.User;
import com.anbuz.anapicommon.model.vo.InterfaceInfoVO;
import com.anbuz.anapibackend.service.InterfaceInfoService;
import com.anbuz.anapibackend.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * API接口
 */
@RestController
@RequestMapping("/interfaceInfo")
@Slf4j
@Api(tags = "APi相关接口")
public class InterfaceInfoController {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    // region 增删改查

    /**
     * 创建
     */
    @PostMapping("/add")
    public BaseResponse<Long> addInterfaceInfo(@RequestBody InterfaceInfoAddDTO interfaceInfoAddDTO) {
        if (interfaceInfoAddDTO == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoAddDTO, interfaceInfo);
        interfaceInfoService.validInterfaceInfo(interfaceInfo, true);
        User loginUser = BaseContext.getCurrentUser();
        interfaceInfo.setUserId(loginUser.getId());
        boolean result = interfaceInfoService.save(interfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.SYSTEM_ERROR);
        long newInterfaceInfoId = interfaceInfo.getId();
        return BaseResponse.success(newInterfaceInfoId);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteInterfaceInfo(@RequestBody DeleteDTO deleteDTO) {
        if (deleteDTO == null || deleteDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        long id = deleteDTO.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NULL_ERROR);
        // 仅本人或管理员可删除
        User user = BaseContext.getCurrentUser();
        if (!oldInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(user)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        boolean b = interfaceInfoService.removeById(id);
        return BaseResponse.success(b);
    }

    /**
     * 更新（仅管理员）
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateInterfaceInfo(@RequestBody InterfaceInfoUpdateDTO interfaceInfoUpdateDTO) {
        if (interfaceInfoUpdateDTO == null || interfaceInfoUpdateDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateDTO, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        long id = interfaceInfoUpdateDTO.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NULL_ERROR);
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return BaseResponse.success(result);
    }

    /**
     * 根据 id 获取
     */
    @GetMapping("/get/vo")
    public BaseResponse<InterfaceInfoVO> getInterfaceInfoVOById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo interfaceInfo = interfaceInfoService.getById(id);
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return BaseResponse.success(interfaceInfoService.getInterfaceInfoVO(interfaceInfo));
    }

    /**
     * 分页获取列表（仅管理员）
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<InterfaceInfo>> listInterfaceInfoByPage(@RequestBody InterfaceInfoQueryDTO interfaceInfoQueryDTO) {
        long current = interfaceInfoQueryDTO.getCurrent();
        long size = interfaceInfoQueryDTO.getPageSize();
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryDTO));
        return BaseResponse.success(interfaceInfoPage);
    }

    /**
     * 分页获取列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<InterfaceInfoVO>> listInterfaceInfoVOByPage(
            @RequestBody InterfaceInfoQueryDTO interfaceInfoQueryDTO) {
        long current = interfaceInfoQueryDTO.getCurrent();
        long size = interfaceInfoQueryDTO.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAM_ERROR);
        Page<InterfaceInfo> interfaceInfoPage = interfaceInfoService.page(new Page<>(current, size),
                interfaceInfoService.getQueryWrapper(interfaceInfoQueryDTO));
        return BaseResponse.success(interfaceInfoService.getInterfaceInfoVOPage(interfaceInfoPage));
    }

    /**
     * 编辑（用户）
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editInterfaceInfo(@RequestBody InterfaceInfoUpdateDTO interfaceInfoUpdateDTO) {
        if (interfaceInfoUpdateDTO == null || interfaceInfoUpdateDTO.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoUpdateDTO, interfaceInfo);
        // 参数校验
        interfaceInfoService.validInterfaceInfo(interfaceInfo, false);
        User loginUser = BaseContext.getCurrentUser();
        long id = interfaceInfoUpdateDTO.getId();
        // 判断是否存在
        InterfaceInfo oldInterfaceInfo = interfaceInfoService.getById(id);
        ThrowUtils.throwIf(oldInterfaceInfo == null, ErrorCode.NULL_ERROR);
        // 仅本人或管理员可编辑
        if (!oldInterfaceInfo.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NOT_AUTH);
        }
        boolean result = interfaceInfoService.updateById(interfaceInfo);
        return BaseResponse.success(result);
    }

}
