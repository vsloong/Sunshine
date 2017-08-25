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
    private static final String STUDENT_ID = "STUDENT_ID";//SharedPreferences中默认学号的键
    private static final String DEFAULT = "";

    public static String getDBName() {
        return DB_NAME;
    }

    public static void setDefaultStudentId(String studentId) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(STUDENT_ID, studentId).apply();
    }

    public static String getDefaultStudentId() {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(STUDENT_ID, DEFAULT);
    }


}
