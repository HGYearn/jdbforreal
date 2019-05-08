package com.jdb.dmp.decisionscenes.conditions;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.domain.JdbUser;

/**
 * Created by qimwang on 11/3/16.
 */
public class FriendCreditAmountA5 implements Condition {
    private int validDays;
    private int thresholdAmount;
    private int isJudgeMeetCondition = ConditionJudgeType.MEET.getValue();

    public FriendCreditAmountA5(int validDays, int thresholdAmount, int isJudgeMeetCondition) {
        this.validDays = validDays;
        this.thresholdAmount = thresholdAmount;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
    }

    public Boolean isSatisfied(JdbUser jdbUser) {
        float amount = jdbUser.getCurrFriendCreditAmountA5();
        if (isJudgeMeetCondition == ConditionJudgeType.MEET.getValue()) {
            if (amount > thresholdAmount) {
                return true;
            } else {
                return false;
            }
        } else {
            if (amount > thresholdAmount) {
                return false;
            } else {
                return true;
            }
        }

    }
}