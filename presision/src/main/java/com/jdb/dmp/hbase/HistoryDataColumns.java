package com.jdb.dmp.hbase;

/**
 * Created by qimwang on 10/14/16.
 */
public enum HistoryDataColumns {
    USER_REGISTER_TIME("user_regist_time"),
    IS_FINISH_BORROW_BUY_FINANCE("is_finish_borrow_buy_finance"),
    FOLLOWEE_CNT("followee_cnt"),
    IS_FINISH_NEWERTASK("is_finish_newertask"),
    USER_REGISTER_DAYS("user_regist_days"),

    IS_OVERDUE("is_overdue"),
    USER_BIRTHDAY("user_birthday"),
    USER_MOBILE("user_mobile"),
    USER_NAME("user_name"),
    USER_ID("userid"),

    IS_DIRECT_LEND("is_can_join_ten"),

    USER_CREDIT_ALL_DAY1("user_credit_all_day1"),
    USER_CREDIT_ALL_DAY2("user_credit_all_day2"),
    USER_CREDIT_ALL_DAY3("user_credit_all_day3"),
    USER_CREDIT_ALL_DAY4("user_credit_all_day4"),
    USER_CREDIT_ALL_DAY5("user_credit_all_day5"),
    USER_CREDIT_ALL_DAY6("user_credit_all_day6"),
    USER_CREDIT_ALL_DAY7("user_credit_all_day7"),
    USER_CREDIT_ALL_DAY8("user_credit_all_day8"),
    USER_CREDIT_ALL_DAY9("user_credit_all_day9"),
    USER_CREDIT_ALL_DAY10("user_credit_all_day10"),
    USER_CREDIT_ALL_DAY11("user_credit_all_day11"),
    USER_CREDIT_ALL_DAY12("user_credit_all_day12"),
    USER_CREDIT_ALL_DAY14("user_credit_all_day13"),
    USER_CREDIT_ALL_DAY15("user_credit_all_day14"),

    USER_TOTAL_AMOUNT_DAY("user_total_amount_day");



    String name;
    public String toString() {
        return name;
    }

    HistoryDataColumns(String name) {
        this.name = name;
    }

}
