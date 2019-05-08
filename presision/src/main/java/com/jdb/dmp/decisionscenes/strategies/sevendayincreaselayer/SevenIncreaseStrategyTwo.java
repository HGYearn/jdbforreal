package com.jdb.dmp.decisionscenes.strategies.sevendayincreaselayer;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.other.NewbieMissionCondition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByDayCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public class SevenIncreaseStrategyTwo implements Strategy {

    private static final long serialVersionUID = 1L;

    private RegisterDateByDayCondition registerDateByDayCondition;
    private NewbieMissionCondition newbieMissionCondition;

    public SevenIncreaseStrategyTwo(int lastRegisterDay, int virtualBidAmount)
    {
        this.registerDateByDayCondition = new RegisterDateByDayCondition(lastRegisterDay, ConditionJudgeType.MEET.getValue(), ConditionJudgeType.EQUAL.getValue());
        this.newbieMissionCondition = new NewbieMissionCondition(virtualBidAmount, ConditionJudgeType.MISS.getValue());
    }

    public List<Condition> getConditions()
    {
        List<Condition> conditionList = new ArrayList<>();

        conditionList.add(this.registerDateByDayCondition);
        conditionList.add(this.newbieMissionCondition);

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
}
