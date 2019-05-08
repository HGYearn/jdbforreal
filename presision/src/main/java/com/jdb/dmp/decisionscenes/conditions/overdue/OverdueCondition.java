package com.jdb.dmp.decisionscenes.conditions.overdue;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.domain.JdbUser;

/**
 * User: niceforbear
 * Date: 16/10/11
 */
public class OverdueCondition implements Condition {
    private static final long serialVersionUID = 1L;

    private int isJudgeMeetCondition = ConditionJudgeType.MEET.getValue();

    public OverdueCondition(int isJudgeMeetCondition) {
        this.isJudgeMeetCondition = isJudgeMeetCondition;
    }

    public Boolean isSatisfied(JdbUser jdbUser)
    {
        if (isJudgeMeetCondition == ConditionJudgeType.MEET.getValue()) {
            if (jdbUser.isHisOverdue() || jdbUser.isCurrOverdue()) {
                return true;
            } else {
                return false;
            }
        } else {
            if (jdbUser.isHisOverdue() || jdbUser.isCurrOverdue()) {
                return false;
            } else {
                return true;
            }
        }
    }
}
