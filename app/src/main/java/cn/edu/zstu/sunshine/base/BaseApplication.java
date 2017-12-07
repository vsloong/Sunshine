package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import cn.edu.zstu.sunshine.BuildConfig;

/**
 * Application的基类
 * Created by CooLoongWu on 2017-8-17 15:26.
 */

public class BaseApplication extends MultiDexApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();

        //根据打包类型判断是否打印日志
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG_MODE;
            }
        });

    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }

}
