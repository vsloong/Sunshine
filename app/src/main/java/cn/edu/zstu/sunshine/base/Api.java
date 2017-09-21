package cn.edu.zstu.sunshine.base;

import cn.edu.zstu.sunshine.utils.OkHttpUtil;
import okhttp3.Callback;

/**
 * 接口数据类
 * Created by CooLoongWu on 2017-9-21 10:22.
 */

public class Api {

    private static final String URL_BASE = "https://easy-mock.com/mock/59acaa94e0dc6633419a3afe/sunshine/";
    private static final String URL_NETWORK = "network";


    public static void getNetworkInfo(Callback callback) {
        OkHttpUtil.getInstance()
                .post(URL_BASE + URL_NETWORK)
                .addParam("userId", AppConfig.getDefaultUserId())
                .build()
                .enqueue(callback);
    }
}
