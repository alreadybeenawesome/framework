package com.centryOf22th.framework.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Created by louis on 16-10-17.
 */


/**
 * the default pattern is "yyyy-MM-dd HH:mm:ss"
 */
public class DateUtil {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";


    /**
     * @return current time with pattern of "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurrentTimeWithDefaultPattern() {
        return getCurrentTimeByPattern(YYYY_MM_DD_HH_MM_SS);
    }


    /**
     * @param pattern "yyyy-MM-dd HH:mm:ss" or others
     * @return the current time with specified time pattern
     */
    public static String getCurrentTimeByPattern(String pattern) {
        String sDate = null;
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            sDate = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sDate;
    }


    /**
     * @param date
     * @param pattern format pattern
     * @return the date String that converted by date with format pattern
     */
    public static String convertToString(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("the date can not be null");
        }
        String s = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            s = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    /**
     * @param date the date
     * @return the date String that converted by date with default pattern "yyyy-MM-dd HH:mm:ss"
     */
    public static String convertToString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("the date can not be null");
        }
        String s = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            s = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static Date convertToDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            throw new IllegalArgumentException("the date can not be null");
        }
        Date vDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            vDate = formatter.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vDate;
    }


    public static void main(String[] args) {
        String s = DateUtil.getCurrentTimeByPattern(YYYY_MM_DD_HH_MM_SS);
        System.out.println(s);
        Date date=DateUtil.convertToDate(s,YYYY_MM_DD_HH_MM_SS);

        System.out.println(date);
        System.out.println(convertToString(date,YYYY_MM_DD_HH_MM_SS));
    }
}
