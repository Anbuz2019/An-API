package com.anbuz.anapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息
 * @TableName interface_info
 */
@TableName(value ="interface_info")
@Data
public class InterfaceInfo implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 接口名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 请求类型
     */
    @TableField(value = "method")
    private String method;

    /**
     * 接口地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 创建人id
     */
    @TableField(value = "userId")
    private Long userId;

    /**
     * 请求头
     */
    @TableField(value = "requestHeader")
    private String requestHeader;

    /**
     * 响应头
     */
    @TableField(value = "responseHeader")
    private String responseHeader;

    /**
     * 接口描述
     */
    @TableField(value = "interfaceDescription")
    private String interfaceDescription;

    /**
     * 接口状态 0-关闭 1-开启
     */
    @TableField(value = "interfaceStatus")
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
     * 逻辑删除
     */
    @TableField(value = "isDelete")
    @TableLogic
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "createTime")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "updateTime")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        InterfaceInfo other = (InterfaceInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRequestHeader() == null ? other.getRequestHeader() == null : this.getRequestHeader().equals(other.getRequestHeader()))
            && (this.getResponseHeader() == null ? other.getResponseHeader() == null : this.getResponseHeader().equals(other.getResponseHeader()))
            && (this.getInterfaceDescription() == null ? other.getInterfaceDescription() == null : this.getInterfaceDescription().equals(other.getInterfaceDescription()))
            && (this.getInterfaceStatus() == null ? other.getInterfaceStatus() == null : this.getInterfaceStatus().equals(other.getInterfaceStatus()))
            && (this.getCostScore() == null ? other.getCostScore() == null : this.getCostScore().equals(other.getCostScore()))
            && (this.getRequestParams() == null ? other.getRequestParams() == null : this.getRequestParams().equals(other.getRequestParams()))
            && (this.getResponseParams() == null ? other.getResponseParams() == null : this.getResponseParams().equals(other.getResponseParams()))
            && (this.getRequestExample() == null ? other.getRequestExample() == null : this.getRequestExample().equals(other.getRequestExample()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRequestHeader() == null) ? 0 : getRequestHeader().hashCode());
        result = prime * result + ((getResponseHeader() == null) ? 0 : getResponseHeader().hashCode());
        result = prime * result + ((getInterfaceDescription() == null) ? 0 : getInterfaceDescription().hashCode());
        result = prime * result + ((getInterfaceStatus() == null) ? 0 : getInterfaceStatus().hashCode());
        result = prime * result + ((getCostScore() == null) ? 0 : getCostScore().hashCode());
        result = prime * result + ((getRequestParams() == null) ? 0 : getRequestParams().hashCode());
        result = prime * result + ((getResponseParams() == null) ? 0 : getResponseParams().hashCode());
        result = prime * result + ((getRequestExample() == null) ? 0 : getRequestExample().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", method=").append(method);
        sb.append(", url=").append(url);
        sb.append(", userId=").append(userId);
        sb.append(", requestHeader=").append(requestHeader);
        sb.append(", responseHeader=").append(responseHeader);
        sb.append(", interfaceDescription=").append(interfaceDescription);
        sb.append(", interfaceStatus=").append(interfaceStatus);
        sb.append(", costScore=").append(costScore);
        sb.append(", requestParams=").append(requestParams);
        sb.append(", responseParams=").append(responseParams);
        sb.append(", requestExample=").append(requestExample);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}