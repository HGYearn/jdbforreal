package com.jdb.dmp.decisionscenes.conditions.registerdate;

import com.jdb.dmp.consts.ConditionJudgeType;
import com.jdb.dmp.decisionscenes.conditions.Condition;
import com.jdb.dmp.domain.JdbUser;

import java.util.Calendar;
import java.util.Date;

/**
 * User: niceforbear
 * Date: 16/10/11
 */
public class RegisterDateByDayCondition implements Condition
{
    private static final long serialVersionUID = 1L;

    private int lastRegisterDay;
    private int isJudgeMeetCondition;
    private int isEqual;

    public RegisterDateByDayCondition(int lastRegisterDay, int isJudgeMeetCondition)
    {
        this.lastRegisterDay = lastRegisterDay;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
        this.isEqual = ConditionJudgeType.EQUAL_NOT_EXIST.getValue();
    }

    public RegisterDateByDayCondition(int lastRegisterDay, int isJudgeMeetCondition, int isEqual)
    {
        this.lastRegisterDay = lastRegisterDay;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
        this.isEqual = isEqual;
    }

    public Boolean isSatisfied(JdbUser jdbUser)
    {
        Date registerDate = jdbUser.getRegisterTime();
        Date currDate = new Date();

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(registerDate);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(currDate);

        long time1 = cal1.getTimeInMillis();
        long time2 = cal2.getTimeInMillis();

        if ((time2 - time1) > lastRegisterDay * 86400 * 1000) {
            return false;
        } else {
            return true;
        }
    }
}
