package com.anbuz.anapibackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义错误码
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(0, "success", ""),
    PARAM_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    CONFLICT_ERROR(40002, "请求数据与数据库冲突", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NOT_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述
     */
    private final String detail;

}
