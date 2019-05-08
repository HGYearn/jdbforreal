package com.jdb.dmp.decisionscenes.strategies.sevendayincreaselayer;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.decisionscenes.conditions.registerdate.RegisterDateByDayCondition;
import com.jdb.dmp.decisionscenes.strategies.Strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * User: niceforbear
 * Date: 16/10/12
 */
public class SevenIncreaseStrategyFour implements Strategy {

    private static final long serialVersionUID = 1L;

    private RegisterDateByDayCondition registerDateByDayCondition;

    public SevenIncreaseStrategyFour(int lastRegisterDay)
    {
        this.registerDateByDayCondition = new RegisterDateByDayCondition(lastRegisterDay, ConditionJudgeType.MEET.getValue(),
                ConditionJudgeType.EQUAL.getValue());
    }

    public List<Condition> getConditions()
    {
        List<Condition> conditionList = new ArrayList<>();

        conditionList.add(this.registerDateByDayCondition);

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
}
