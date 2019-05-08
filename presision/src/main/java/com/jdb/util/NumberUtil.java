package com.jdb.util;

/**
 * Created by zhouqf on 16/10/11.
 */
public class NumberUtil {
    public static String formatNumber(int d, int lenth, String c)
    {
        return  String.format("%" + c + lenth + "d", d);
    }
}
