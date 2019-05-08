package com.jdb.dmp.decisionscenes.conditions;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.domain.JdbUser;

/**
 * Created by qimwang on 11/3/16.
 */
public class FriendLendRecallA3 implements Condition {
    private int validDays;
    private int thresholdRate;
    private int isJudgeMeetCondition = ConditionJudgeType.MEET.getValue();

    public FriendLendRecallA3(int validDays, int thresholdRate, int isJudgeMeetCondition) {
        this.validDays = validDays;
        this.thresholdRate = thresholdRate;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
    }

    public Boolean isSatisfied(JdbUser jdbUser) {
        float rate = jdbUser.getCurrFriendLendRateA3();
        if (isJudgeMeetCondition == ConditionJudgeType.MEET.getValue()) {
            if (rate > thresholdRate) {
                return true;
            } else {
                return false;
            }
        } else {
            if (rate > thresholdRate) {
                return false;
            } else {
                return true;
            }
        }

    }
}
