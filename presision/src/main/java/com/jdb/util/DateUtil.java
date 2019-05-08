package com.jdb.util;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhouqf on 16/10/11.
 */
public class DateUtil {

    public static final SimpleDateFormat LONG_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat HH_SDF = new SimpleDateFormat("HH");

    public static final SimpleDateFormat NORMAL_SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static final int SYNC_INTERVALS = 3600 * 2;

    public static final String ZERO_DATE = "0000-00-00 00:00:00";

    public static String getSyncFromTime()
    {
        Date now = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.HOUR, - 60 * 2);
        now = cal.getTime();
        String hour = HH_SDF.format(now);
        String d = NORMAL_SDF.format(now);
        return d + ' ' + hour + ":00:00";
    }

    public static String getLongDate(Date d)
    {
        return LONG_SDF.format(d);
    }

    public static String getSyncEndTime()
    {
        Date now = new Date();
        String hour = HH_SDF.format(now);
        String d = NORMAL_SDF.format(now);
        return d + ' ' + hour + ":00:00";
    }
    public static String getStartTimeOfToday()
    {
        Date now = new Date();
        String hour = HH_SDF.format(now);
        String d = NORMAL_SDF.format(now);
        return d + ' ' + "00:00:00";
    }
}
