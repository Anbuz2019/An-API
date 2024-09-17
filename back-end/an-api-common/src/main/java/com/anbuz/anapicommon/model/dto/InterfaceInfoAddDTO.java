package com.anbuz.anapicommon.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceInfoAddDTO {

    /**
     * 接口名称
     */
    private String name;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 接口描述
     */
    private String interfaceDescription;

    /**
     * 接口状态 0-关闭 1-开启
     */
    private Integer interfaceStatus;

    /**
     * 消耗积分
     */
    private Integer costScore;

    /**
     * 请求参数
     */
    private Object requestParams;

    /**
     * 响应参数
     */
    private Object responseParams;

    /**
     * 请求示例
     */
    private Object requestExample;

}
