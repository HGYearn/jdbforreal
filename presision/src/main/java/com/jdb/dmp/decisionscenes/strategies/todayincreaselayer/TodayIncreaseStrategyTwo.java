package com.jdb.dmp.decisionscenes.strategies.todayincreaselayer;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.other.BorrowToBuyFinProdCondition;
import com.jdb.dmp.decisionscenes.conditions.other.NewbieMissionCondition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByHourCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 *
 * 策略二：对所有截止到运算时近24小时内注册用户进行判定，完成新手任务的用户但是未完成借款买理财的用户
 */
public class TodayIncreaseStrategyTwo implements Strategy
{
    private static final long serialVersionUID = 1L;

    private RegisterDateByHourCondition registerDateByHourCondition;
    private NewbieMissionCondition newbieMissionCondition;
    private BorrowToBuyFinProdCondition borrowToBuyFinProdCondition;

    public TodayIncreaseStrategyTwo(int lastRegisterHour, int virtualBidAmount, int borrowToBuyFinProdAmount)
    {
        this.registerDateByHourCondition = new RegisterDateByHourCondition(lastRegisterHour, ConditionJudgeType.MEET.getValue());
        this.newbieMissionCondition = new NewbieMissionCondition(virtualBidAmount, ConditionJudgeType.MEET.getValue());
        this.borrowToBuyFinProdCondition = new BorrowToBuyFinProdCondition(borrowToBuyFinProdAmount, ConditionJudgeType.MISS.getValue());
    }

    public List<Condition> getConditions()
    {
        List<Condition> conditionList = new ArrayList<>();

        conditionList.add(this.registerDateByHourCondition);
        conditionList.add(this.newbieMissionCondition);
        conditionList.add(this.borrowToBuyFinProdCondition);

        return conditionList;
    }

    public RegisterDateByHourCondition getRegisterDateByHourCondition()
    {
        return registerDateByHourCondition;
    }

    public void setRegisterDateByHourCondition(RegisterDateByHourCondition registerDateByHourCondition)
    {
        this.registerDateByHourCondition = registerDateByHourCondition;
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
