package com.anbuz.anapibackend.model.dto;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceInfoUpdateDTO {
    /**
     * id
     */
    private Long id;
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
}
