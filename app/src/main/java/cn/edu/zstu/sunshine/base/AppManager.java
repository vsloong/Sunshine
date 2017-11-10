package cn.edu.zstu.sunshine.base;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Activity管理类(多进程待完善)
 * Created by CooLoongWu on 2017-11-10 14:23.
 */

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager appManager;

    public static AppManager getInstance() {
        if (null == appManager) {
            appManager = new AppManager();
        }
        return appManager;
    }

    /**
     * 添加Activity
     *
     * @param activity Activity
     */
    public void addActivity(Activity activity) {
        if (null == activityStack) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * @return 当前的Activity
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity() {
        Activity activity = activityStack.lastElement();
        if (null != activity) {
            activity.finish();
        }
    }

    /**
     * 结束指定Activity
     *
     * @param activity Activity
     */
    public void finishActivity(Activity activity) {
        if (null != activity) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls Class
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityStack.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 退出App
     *
     * @param context 上下文
     */
    public void exitApp(Context context) {
        finishAllActivity();
        System.exit(0);
    }
}
