package com.jdb.dmp.consts;

/**
 * User: niceforbear
 * Date: 16/10/11
 */
public enum LayerType {
    TODAY_INCREASE_USER_ENUM(1, "当日新增用户"),
    SEVEN_DAY_INCREASE_USER_ENUM(2, "7日新增用户"),
    LAST_DAY_INCREASE_USER_ENUM(3, "近日新增用户");

    public final int key;
    public final String value;

    LayerType(int key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
