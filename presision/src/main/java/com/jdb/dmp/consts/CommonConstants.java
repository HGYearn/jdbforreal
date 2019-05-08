package com.jdb.dmp.consts;

/**
 * User: niceforbear
 * Date: 16/10/14
 */
public enum CommonConstants {
    GENERATE_BYTES(65536, "生成bytes的大小");

    public final int key;
    public final String value;

    CommonConstants(int key, String value)
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
