package cn.edu.zstu.sunshine.utils;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;

import cn.edu.zstu.sunshine.R;
import cn.edu.zstu.sunshine.base.BaseApplication;

/**
 * 日期、时间的工具类
 * Created by CooLoongWu on 2017-8-15 16:23.
 */

public class DataUtil {

    public static String week[] = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private static final int FIRST_DAY = Calendar.SUNDAY;

    /**
     * 得到当前月份
     *
     * @return int
     */
    private static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static SpannableString getMonthString() {
        String month = String.format(BaseApplication.getAppContext().getResources().getString(R.string.month), DataUtil.getMonth());
        return getColorText(month, R.color.text_yellow, 0, month.length() - 1);
    }

    /**
     * 得到当前周数
     *
     * @return int
     */
    public static int getWeek() {
        return 0;
    }

    /**
     * 得到今天是本周第几天（注：周一是第一天）
     *
     * @return int
     */
    public static int getDayOfWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 得到本周的日期
     *
     * @return int[]
     */
    public static int[] getDates() {
        int date[] = new int[7];
        Calendar calendar = Calendar.getInstance();
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY) {
            calendar.add(Calendar.DATE, -1);
        }
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            date[i] = calendar.get(Calendar.DATE);
        }
        return date;
    }

    private static SpannableString getColorText(String string, int color, int start, int end) {
        SpannableString sp = new SpannableString(string);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(BaseApplication.getAppContext(), color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
