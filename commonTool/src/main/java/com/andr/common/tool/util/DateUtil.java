package com.andr.common.tool.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <pre>
 *     author: momoxiaoming
 *     time  : 2019/8/5
 *     desc  : new class
 * </pre>
 */
public class DateUtil
{

    /**
     * 格式化时间并返回毫秒数
     *
     * @param time    12:00
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeMillisecond(String time, String pattern)
    {
        try
        {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(pattern);
            Date date = format.parse(time);
            return date.getTime();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }


        return 0;
    }


    /**
     * 根据毫秒数返回格式化时间
     *
     * @param time    12:00
     * @param pattern yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getTimeMillisecond(long time, String pattern)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(time);


    }
}
