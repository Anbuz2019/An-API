package com.anbuz.anapibackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceInfoQueryDTO {

    private Long id;

    /**
     * 分页参数
     */
    private int current;

    private int pageSize;

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
     * 创建人id
     */
    private Long userId;

    /**
     * 查询关键字
     */
    public String searchText;

    /**
     * 接口状态 0-关闭 1-开启
     */
    private Integer interfaceStatus;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序规则
     */
    private String sortOrder;
}
