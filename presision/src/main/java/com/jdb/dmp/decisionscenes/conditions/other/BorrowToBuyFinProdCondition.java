package com.jdb.dmp.decisionscenes.conditions.other;

import com.google.common.base.Preconditions;
import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.domain.JdbUser;

import java.util.Date;


/**
 * User: niceforbear
 * Date: 16/10/11
 */
public class BorrowToBuyFinProdCondition implements Condition {
    private static final long serialVersionUID = 1L;

    private int borrowToBuyFinProdAmount;
    private int isJudgeMeetCondition;

    public BorrowToBuyFinProdCondition(int borrowToBuyFinProdAmount, int isJudgeMeetCondition) {
        this.borrowToBuyFinProdAmount = borrowToBuyFinProdAmount;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
    }

    public Boolean isSatisfied(JdbUser jdbUser) {
        try {
            Date date = Preconditions.checkNotNull(jdbUser.getRegisterTime());
            long currTime = System.currentTimeMillis();
            long regTime = date.getTime();
            if (isJudgeMeetCondition == ConditionJudgeType.MEET.getValue()) {
                if (jdbUser.getCurrRepayFlow() != null || (jdbUser.getHisHasBuyLicaiProduct() != null &&
                        Integer.valueOf(Preconditions.checkNotNull(jdbUser.getHisHasBuyLicaiProduct())).intValue() == 1)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (jdbUser.getCurrRepayFlow() != null || (jdbUser.getHisHasBuyLicaiProduct() != null &&
                        Integer.valueOf(Preconditions.checkNotNull(jdbUser.getHisHasBuyLicaiProduct())).intValue() == 1)) {
                    return false;
                } else {
                    return true;
                }
            }

        } catch (Exception e) {
            return false;
        }
    }
}
