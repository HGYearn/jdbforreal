package com.jdb.dmp.datasource;

/**
 *
 */
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> holder   = new ThreadLocal<>();
    public static String DMP_DATA_SOURCE     = "dmp";
    public static String DITUI_DATA_SOURCE   = "ditui";
    public static String PHOENIX_DATA_SOURCE = "phoenix";
    public static String BIGDATA1_JDB_DATA_SOURCE = "bigdata1_jdb";
    public static String BIGDATA1_PASSPORT_DATA_SOURCE = "bigdata1_passport";
    public static String BIGDATA2_JDB_DATA_SOURCE = "bigdata2_jdb";

    //通讯录表
    public static String DHB1_DATASOURCE = "dhb1";
    public static String DHB2_DATASOURCE = "dhb2";
    public static String DHB3_DATASOURCE = "dhb3";
    public static String DHB4_DATASOURCE = "dhb4";
    public static String DHB5_DATASOURCE = "dhb5";
    public static String DHB6_DATASOURCE = "dhb6";
    public static String DHB7_DATASOURCE = "dhb7";
    public static String DHB8_DATASOURCE = "dhb8";
    public static String DHB9_DATASOURCE = "dhb9";
    public static String DHB10_DATASOURCE = "dhb10";
    public static String DHB11_DATASOURCE = "dhb11";
    public static String DHB12_DATASOURCE = "dhb12";
    public static String DHB13_DATASOURCE = "dhb13";
    public static String DHB14_DATASOURCE = "dhb14";
    public static String DHB15_DATASOURCE = "dhb15";
    public static String DHB16_DATASOURCE = "dhb16";

    public static String UR_DATASOURCE = "ur";


    public static String getDataSource() {
        String db = holder.get();
        if (db == null) {
            db = DMP_DATA_SOURCE;
        } else if(db.equals("ditui")){
            db = DITUI_DATA_SOURCE;
        } else if (db.equals("phoenix")) {
            db = PHOENIX_DATA_SOURCE;
        } else if(db.equals("bigdata1_jdb")) {
            db = BIGDATA1_JDB_DATA_SOURCE;
        } else if(db.equals("bigdata1_passport")) {
            db = BIGDATA1_PASSPORT_DATA_SOURCE;
        } else if(db.equals("bigdata2_jdb")) {
            db = BIGDATA2_JDB_DATA_SOURCE;
        } else if (db.equals("dhb1")) {
            db = DHB1_DATASOURCE;
        } else if (db.equals("dhb2")) {
            db = DHB2_DATASOURCE;
        } else if (db.equals("dhb3")) {
            db = DHB3_DATASOURCE;
        } else if (db.equals("dhb4")) {
            db = DHB4_DATASOURCE;
        } else if (db.equals("dhb5")) {
            db = DHB5_DATASOURCE;
        } else if (db.equals("dhb6")) {
            db = DHB6_DATASOURCE;
        } else if (db.equals("dhb7")) {
            db = DHB7_DATASOURCE;
        } else if (db.equals("dhb8")) {
            db = DHB8_DATASOURCE;
        } else if (db.equals("dhb9")) {
            db = DHB9_DATASOURCE;
        } else if (db.equals("dhb10")) {
            db = DHB10_DATASOURCE;
        } else if (db.equals("dhb11")) {
            db = DHB11_DATASOURCE;
        } else if (db.equals("dhb12")) {
            db = DHB12_DATASOURCE;
        } else if (db.equals("dhb13")) {
            db = DHB13_DATASOURCE;
        } else if (db.equals("dhb14")) {
            db = DHB14_DATASOURCE;
        } else if (db.equals("dhb15")) {
            db = DHB15_DATASOURCE;
        } else if (db.equals("dhb16")) {
            db = DHB16_DATASOURCE;
        } else if (db.equals("ur")) {
            db = UR_DATASOURCE;
        } else {
            db = DMP_DATA_SOURCE;
        }

        return db;
    }

    /**
     * 设置当前线程的dataSource
     */
    public static void setDataSource(String str) {
        holder.set(str);
    }

    /**
     * 清理当前线程的dataSource
     */
    public static void clearDataSource() {
        holder.remove();
    }


}

