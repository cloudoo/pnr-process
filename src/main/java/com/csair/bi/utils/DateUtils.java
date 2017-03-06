package com.csair.bi.utils;

/**
 * Created by cloudoo on 2015/8/11.
 */
public class DateUtils {

    public static String string2HiveDateFormat(String yyyymmdd){

        String hiveDateFormat = yyyymmdd.substring(0,4)+"-"+yyyymmdd.substring(4,6)+"-"+yyyymmdd.substring(6,8);

        return hiveDateFormat;
    }

    public static String string2HiveTimeFormat(String yyyyMMddhhmm){

        String hiveTimeFormat = yyyyMMddhhmm.substring(0,4)+"-"+yyyyMMddhhmm.substring(4,6)+"-"
                +yyyyMMddhhmm.substring(6,8)+" "+yyyyMMddhhmm.substring(8,10)+":"+yyyyMMddhhmm.substring(10,12)+":00";

        return hiveTimeFormat;
    }
    public static String hhmm2second(String hhmm){
        int temp = Integer.valueOf(hhmm);
        return String.valueOf((temp/100)*60+(temp%100));
    }

    public static String hhmmFormat(String hhmm){

        return hhmm.substring(0,2)+":"+hhmm.substring(2,4)+":00";
    }

    public static String yyMMdd2HiveDate(String yyMMdd){
        String[] a = yyMMdd.split("/");
        if(a.length<=2)
            return "";
        else return "20"+a[0]+"-"+a[1]+"-"+a[2];
    }

    public static void main(String[] args){

        System.out.println(DateUtils.yyMMdd2HiveDate("15/1/15"));
    }
}
