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
public class RegisterDateByHourCondition implements Condition
{
    private static final long serialVersionUID = 1L;

    /**
     * 最近注册时间的时间计算距离
     */
    private int lastRegisterHour;
    private int isJudgeMeetCondition;
    private int isEqual;

    public RegisterDateByHourCondition(int lastRegisterHour, int isJudgeMeetCondition)
    {
        this.lastRegisterHour = lastRegisterHour;
        this.isJudgeMeetCondition = isJudgeMeetCondition;
        this.isEqual = ConditionJudgeType.EQUAL_NOT_EXIST.getValue();
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

        if ((time2 - time1) > lastRegisterHour * 3600 * 1000) {
            return false;
        } else {
            return true;
        }
    }
}
