package com.anbuz.anapicommon.model.enums;

/**
 * 队伍状态枚举
 */
public enum TeamStatusEnum {

    PUBLIC(0, "公开"),
    PRIVATE(1, "私有"),
    SECRET(2, "私密");

    private int value;

    private String description;

    public static TeamStatusEnum getEnumByValue(Integer value) {
        if (value == null) return null;
        TeamStatusEnum[] enums = TeamStatusEnum.values();
        for (TeamStatusEnum e : enums) {
            if (e.getValue() == value) {
                return e;
            }
        }
        return null;
    }

    TeamStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
