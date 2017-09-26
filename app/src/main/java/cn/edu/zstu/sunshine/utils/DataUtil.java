package cn.edu.zstu.sunshine.utils;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseApplication;

/**
 * 日期、时间的工具类
 * Created by CooLoongWu on 2017-8-15 16:23.
 */

public class DataUtil {

    public static String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

    /**
     * @return 当前月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 从字符串中获取月份
     *
     * @param dataStr 日期时间字符串
     * @return 月份
     */
    public static int getMonthFromString(String dataStr) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(dataStr);
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }


    }

    /**
     * @return 彩色的月份信息
     */
    public static SpannableString getMonthString() {
        String month = String.format(BaseApplication.getAppContext().getResources().getString(R.string.month), DataUtil.getMonth());
        return getColorText(month, R.color.text_yellow, 0, month.length() - 1);
    }

    /**
     * @return 当前校历中当前的周数
     */
    public static int getWeek() {
        return 0;
    }

    public static SpannableString getWeekString() {
        String week = "第6周";
        return getColorText(week, R.color.text_yellow, 1, week.length() - 1);
    }


    /**
     * @return 今天是本周第几天（注：周一是1）
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0)
            dayOfWeek = 7;
        Logger.e("日期，周" + dayOfWeek);
        return dayOfWeek;
    }

    /**
     * @return 本周包含的日期
     */
    public static int[] getDatesOfWeek() {
        int date[] = new int[7];
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            date[i] = calendar.get(Calendar.DATE);
        }
        return date;
    }

    /**
     * 修改一段String中某些字符串为彩色
     *
     * @param string 要修改的字符串
     * @param color  颜色
     * @param start  开始位置
     * @param end    结束为止
     * @return 修改后的字符串
     */
    private static SpannableString getColorText(String string, int color, int start, int end) {
        SpannableString sp = new SpannableString(string);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(BaseApplication.getAppContext(), color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
