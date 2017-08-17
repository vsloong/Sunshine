package cn.edu.zstu.sunshine.base;

import android.app.Application;
import android.content.Context;

/**
 * Application的基类
 * Created by CooLoongWu on 2017-8-17 15:26.
 */

public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }
}
