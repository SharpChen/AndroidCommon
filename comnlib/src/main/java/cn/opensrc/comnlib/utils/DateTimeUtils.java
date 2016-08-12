package cn.opensrc.comnlib.utils;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Author:       sharp
 * Created on:   8/12/16 5:22 PM
 * Description:  日期,时间
 * Revisions:
 */
public final class DateTimeUtils {
    private DateTimeUtils() {
    }

    private static ValueAnimator mTimingValueAnimator;

    /**
     * 开始计时
     *
     * @param totalSeconds   总共计时多少秒
     * @param delaySeconds   延迟多少秒开始计时
     * @param period         计时周期
     * @param updateListener 当前监听器
     * @param listener       整个动画监听器
     */
    public static void startTiming(int totalSeconds,
                                   int delaySeconds,
                                   int period,
                                   ValueAnimator.AnimatorUpdateListener updateListener,
                                   AnimatorListenerAdapter listener) {
        mTimingValueAnimator = ValueAnimator.ofInt(0, totalSeconds);
        mTimingValueAnimator.setDuration(totalSeconds * 1000 * period);
        mTimingValueAnimator.setInterpolator(new LinearInterpolator());
        mTimingValueAnimator.setStartDelay(delaySeconds * 1000);
        mTimingValueAnimator.addUpdateListener(updateListener);
        mTimingValueAnimator.addListener(listener);
        mTimingValueAnimator.start();
    }

    /**
     * 停止计时
     */
    public static void stopTiming() {
        if (mTimingValueAnimator != null && mTimingValueAnimator.isRunning()) {
            mTimingValueAnimator.cancel();
            mTimingValueAnimator = null;
        }
    }

    /**
     * 秒表,在 updateListener 回调接口中取到当前秒
     *
     * @param totalSec       总共秒表需要执行多少秒
     * @param updateListener 当前秒监听器
     * @param listener       整个动画监听器
     */
    public static void startWatch(int totalSec,
                                  ValueAnimator.AnimatorUpdateListener updateListener,
                                  AnimatorListenerAdapter listener) {

        startTiming(totalSec, 0, 1, updateListener, listener);

    }

    /**
     * 将 Date 对象格式化为指定格式的日期字符串
     *
     * @param date   Date 对象
     * @param format 日期格式,当用日期格式:
     *               yyyy-MM-dd HH:mm:ss(H:24小时制,h:12小时制)
     *               yyyy年MM月dd日 HH时mm分ss秒
     *               yyyy-MM-dd'T'HH:mm:ss.SSS'Z'(格林威治时间)
     * @return format String
     */
    public static String date2String(Date date, String format) {

        if (date == null || format == null) return "";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
        return dateFormat.format(date);

    }

    /**
     * 将日期格式的字符串转成 Date 对象
     *
     * @param strDate the Date String
     * @param format  the format String
     * @return Date
     */
    public static Date string2Date(String strDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化格林威治时间(2011-01-11T00:00:00.000Z)
     *
     * @param strGMT GMT String
     * @param format fomat String
     * @return the format String
     */
    public static String formatGMTStr(String strGMT, String format) {

        String strFormat = "";
        if (strGMT == null || "".equals(strGMT)) return strFormat;
        Date gmtDate = string2Date(strGMT, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        strFormat = date2String(gmtDate, format);
        return strFormat;

    }


}
