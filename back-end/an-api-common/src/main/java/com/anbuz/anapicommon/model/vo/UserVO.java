package com.anbuz.anapicommon.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 账号
     */
    @TableField(value = "userAccount")
    private String userAccount;

    /**
     * 用户头像
     */
    @TableField(value = "avatarUrl")
    private String avatarUrl;

    /**
     * 性别
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 状态 0 - 正常
     */
    @TableField(value = "userStatus")
    private Integer userStatus;

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

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    @TableField(value = "userRole")
    private Integer userRole;

    /**
     * 星球编号
     */
    @TableField(value = "planetCode")
    private String planetCode;

    /**
     * 标签 json 列表
     */
    @TableField(value = "tags")
    private String tags;

}
