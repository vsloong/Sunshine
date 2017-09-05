package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * App的配置类
 * Created by CooLoongWu on 2017-8-25 11:06.
 */

public class AppConfig {

    private static final String DB_NAME = "sunshine";//数据库名称
    private static final String SP_NAME = "sunshine";//SharedPreferences名称
    private static final String USER_ID = "USER_ID";//SharedPreferences中默认学号的键
    private static final String MODE_NIGHT = "MODE_NIGHT";//SharedPreferences中日间夜间模式键

    public static final String KEY_MEIQIA = "d7a224565c67228db92576ec3897d980";

    public static String getDBName() {
        return DB_NAME;
    }

    public static void setDefaultUserId(String userId) {
        getSP().edit().putString(USER_ID, userId).apply();
    }

    public static String getDefaultUserId() {
        return getSP().getString(USER_ID, "");
    }

    public static void setNightMode(boolean isNight) {
        getSP().edit().putBoolean(MODE_NIGHT, isNight).apply();
    }

    public static boolean isNightMode() {
        return getSP().getBoolean(MODE_NIGHT, false);
    }

    private static SharedPreferences getSP() {
        return BaseApplication.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
