package com.kabu.blog.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat sf = null;

    // 将时间戳转成字符串
    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sf.format(d);
        return format;
    }

    //获取当前时间
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    //将字符串转换为时间戳
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    //直接获取当前时间戳
    public static String getTimeStamp() {
        String currentDate = getCurrentDate();
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }
    //将时间戳转换为s为单位
    public static long getTimes(long timestamp){
        Date date = new Date(timestamp);
        long seconds = date.getTime() / 1000;
        System.out.println(seconds);
        return seconds;
    }

    public static void main(String[] args) {
        getTimes(1701146726323L);
    }
}
