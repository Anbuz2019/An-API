package com.anbuz.anapicommon.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 创建人id
     */
    private Long userId;

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
    @TableField(value = "costScore")
    private Integer costScore;

    /**
     * 请求参数
     */
    @TableField(value = "requestParams")
    private Object requestParams;

    /**
     * 响应参数
     */
    @TableField(value = "responseParams")
    private Object responseParams;

    /**
     * 请求示例
     */
    @TableField(value = "requestExample")
    private Object requestExample;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
