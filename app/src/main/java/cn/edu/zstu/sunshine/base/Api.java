package cn.edu.zstu.sunshine.base;

import android.content.Context;

import cn.edu.zstu.sunshine.utils.OkHttpUtil;
import okhttp3.Callback;

/**
 * 接口数据类
 * Created by CooLoongWu on 2017-9-21 10:22.
 */

public class Api {

    public static final String URL_BASE = "https://easy-mock.com/mock/59acaa94e0dc6633419a3afe/sunshine/";
    public static final String URL_NETWORK = "network";
    public static final String URL_CAMPUSCARD = "campuscard";
    public static final String URL_EXAM = "exam";
    public static final String URL_EXERCISE = "exercise";
    public static final String URL_SCORE = "score";
    public static final String URL_TIMETABLE = "timetable";
    public static final String URL_LIBRARY = "library";
    public static final String URL_UPDATE = "update";

    static final int ERR_CODE_400 = 400;
    static final int ERR_CODE_404 = 404;

    /**
     * 获取网费信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getNetworkInfo(Context context, Callback callback) {
        post(context, URL_NETWORK, callback);
    }

    /**
     * 获取饭卡消费信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getCampusCardInfo(Context context, Callback callback) {
        post(context, URL_CAMPUSCARD, callback);
    }

    /**
     * 获取考试信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getExamInfo(Context context, Callback callback) {
        post(context, URL_EXAM, callback);
    }

    /**
     * 获取课表信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getTimetableInfo(Context context, Callback callback) {
        post(context, URL_TIMETABLE, callback);
    }

    /**
     * 获取成绩信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getScoreInfo(Context context, Callback callback) {
        post(context, URL_SCORE, callback);
    }


    /**
     * 获取升级和换肤信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getUpdateInfo(Context context, Callback callback) {
        post(context, URL_UPDATE, callback);
    }

    /**
     * 获取图书馆借书信息
     *
     * @param context  上下文，作为TAG
     * @param callback 回调
     */
    public static void getLibraryInfo(Context context, Callback callback) {
        post(context, URL_LIBRARY, callback);
    }

    static void post(Context context, String url, Callback callback) {
        OkHttpUtil.getBuilder()
                .tag(context)
                .post()
                .url(URL_BASE + url)
                .addParam("userId", AppConfig.getDefaultUserId())
                .build()
                .enqueue(callback);
    }

    public static void download(Context context, String url, Callback callback) {
        OkHttpUtil.getBuilder()
                .tag(context)
                .get()
                .url(url)
                .build()
                .enqueue(callback);
    }

    public static void cancel(Context context) {
        OkHttpUtil.getInstance().cancel(context);
    }
}
