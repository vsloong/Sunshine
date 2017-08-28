package cn.edu.zstu.sunshine.utils;

import android.widget.Toast;

import cn.edu.zstu.sunshine.base.BaseApplication;

/**
 * Toast工具类
 * Created by CooLoongWu on 2017-8-28 13:55.
 */

public class ToastUtil {
    public static void showShortToast(int str) {
        Toast.makeText(BaseApplication.getAppContext(), BaseApplication.getAppContext().getString(str), Toast.LENGTH_SHORT).show();
    }
}
