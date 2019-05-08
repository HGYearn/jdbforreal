package com.jdb.dmp.decisionscenes.strategies.sevendayincreaselayer;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.other.BorrowToBuyFinProdCondition;
import com.jdb.dmp.decisionscenes.conditions.other.NewbieMissionCondition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByDayCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public class SevenIncreaseStrategyThree implements Strategy {

    private static final long serialVersionUID = 1L;

    private RegisterDateByDayCondition registerDateByDayCondition;
    private NewbieMissionCondition newbieMissionCondition;
    private BorrowToBuyFinProdCondition borrowToBuyFinProdCondition;

    public SevenIncreaseStrategyThree(int lastRegisterDay, int virtualBidAmount, int borrowToBuyFinProdAmount)
    {
        this.registerDateByDayCondition = new RegisterDateByDayCondition(lastRegisterDay, ConditionJudgeType.MEET.getValue(), ConditionJudgeType.EQUAL.getValue());
        this.newbieMissionCondition = new NewbieMissionCondition(virtualBidAmount, ConditionJudgeType.MEET.getValue());
        this.borrowToBuyFinProdCondition = new BorrowToBuyFinProdCondition(borrowToBuyFinProdAmount, ConditionJudgeType.MISS.getValue());
    }

    public List<Condition> getConditions()
    {
        List<Condition> conditionList = new ArrayList<>();

        conditionList.add(registerDateByDayCondition);
        conditionList.add(newbieMissionCondition);
        conditionList.add(borrowToBuyFinProdCondition);

        return conditionList;
    }


    public RegisterDateByDayCondition getRegisterDateByDayCondition()
    {
        return registerDateByDayCondition;
    }

    public void setRegisterDateByDayCondition(RegisterDateByDayCondition registerDateByDayCondition)
    {
        this.registerDateByDayCondition = registerDateByDayCondition;
    }

    public NewbieMissionCondition getNewbieMission()
    {
        return newbieMissionCondition;
    }

    public void setNewbieMission(NewbieMissionCondition newbieMission)
    {
        this.newbieMissionCondition = newbieMission;
    }

    public BorrowToBuyFinProdCondition getBorrowToBuyFinProdCondition()
    {
        return borrowToBuyFinProdCondition;
    }

    public void setBorrowToBuyFinProdCondition(BorrowToBuyFinProdCondition borrowToBuyFinProdCondition)
    {
        this.borrowToBuyFinProdCondition = borrowToBuyFinProdCondition;
    }
}
