package com.jdb.dmp.decisionscenes.conditions.other;

import com.google.common.base.Preconditions;
import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.domain.JdbUser;

/**
 * User: niceforbear
 * Date: 16/10/11
 *
 * 完成新手任务：投资了一笔虚拟标
 */
public class NewbieMissionCondition implements Condition
{
    private static final long serialVersionUID = 1L;

    private int virtualBidAmount;
    private int isJudgeMeetCondition;

    /**
     * 初始化构建条件的临界值
     * @param virtualBidAmount int 虚拟标的的完成数
     */
    public NewbieMissionCondition(int virtualBidAmount, int isJudgeMeetCondition)
    {
        this.virtualBidAmount = virtualBidAmount;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
    }

    public Boolean isSatisfied(JdbUser jdbUser)
    {
        if (isJudgeMeetCondition == ConditionJudgeType.MEET.getValue()) {
            if (jdbUser.getCurrVirtualRepayFlow() != null ||
                    (jdbUser.getHisHasFinishNewerTask() != null && Integer.valueOf(Preconditions.checkNotNull(jdbUser.getHisHasFinishNewerTask())).intValue() == 1)
                    ) {
                return true;
            } else {
                return false;
            }
        } else {
            if (jdbUser.getCurrVirtualRepayFlow() != null ||
                    (jdbUser.getHisHasFinishNewerTask() != null && Integer.valueOf(Preconditions.checkNotNull(jdbUser.getHisHasFinishNewerTask())).intValue() == 1)
                    ) {
                return false;
            } else {
                return true;
            }
        }
    }
}
