package com.jdb.dmp.consts;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public enum ConditionJudgeType {
    MEET(1, "满足条件"),
    MISS(2, "不满足条件"),
    EQUAL(3, "相等"),
    NOTEQUAL(4, "不相等"),
    EQUAL_NOT_EXIST(5, "equal条件判断不存在");

    int key;
    String value;

    ConditionJudgeType(int key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public int getValue() {
        return key;
    }

    public String toString() {
        return value;
    }
}
