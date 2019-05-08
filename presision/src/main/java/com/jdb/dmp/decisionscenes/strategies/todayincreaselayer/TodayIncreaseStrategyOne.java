package com.jdb.dmp.decisionscenes.strategies.todayincreaselayer;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.other.NewbieMissionCondition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByHourCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 *
 * 策略1: 对所有截止到运算时近24小时内注册用户进行判定，未完成新手任务的用户
 */
public class TodayIncreaseStrategyOne implements Strategy
{
    private static final long serialVersionUID = 1L;

    private RegisterDateByHourCondition registerDateByHourCondition;
    private NewbieMissionCondition newbieMissionCondition;

    public TodayIncreaseStrategyOne(int lastRegisterHour, int virtualBidAmount)
    {
        this.registerDateByHourCondition = new RegisterDateByHourCondition(lastRegisterHour, ConditionJudgeType.MEET.getValue());
        this.newbieMissionCondition = new NewbieMissionCondition(virtualBidAmount, ConditionJudgeType.MISS.getValue());
    }

    public List<Condition> getConditions()
    {
        List<Condition> conditionList = new ArrayList<>();

        conditionList.add(this.registerDateByHourCondition);
        conditionList.add(this.newbieMissionCondition);

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

    public NewbieMissionCondition getNewbieMissionCondition()
    {
        return newbieMissionCondition;
    }

    public void setNewbieMissionCondition(NewbieMissionCondition newbieMissionCondition)
    {
        this.newbieMissionCondition = newbieMissionCondition;
    }
}
