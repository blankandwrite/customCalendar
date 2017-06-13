package com.god.gl.customcalendar.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取时间
 */
public class CalendarUtils {

    public static String formatCalendar(Calendar calendar, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取时间格式 时：分
     *
     * @param mCalendar
     * @return
     */
    public static String getTime(Calendar mCalendar) {
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCalendar.get(Calendar.MINUTE);
        return mHour + ":" + mMinute;
    }

    /**
     * 获取时间格式 时：分
     *
     * @param mCalendar
     * @return
     */
    public static int getYear(Calendar mCalendar) {
        return mCalendar.get(Calendar.YEAR);
    }

    /**
     * 获取时间格式 时：分
     *
     * @param mCalendar
     * @return
     */
    public static int getMonth(Calendar mCalendar) {
        return mCalendar.get(Calendar.MONTH) + 1;
    }

    public static String getDay(Calendar mCalendar) {
        if (mCalendar != null) {
            return mCalendar.get(Calendar.DAY_OF_MONTH) + "";
        }
        return "";
    }

    public static Calendar formatDate(String time, String format) {
        Calendar calendar = null;
        try {
            if (!TextUtils.isEmpty(time)) {
                calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                Date date = dateFormat.parse(time);
                calendar.setTime(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }


}
