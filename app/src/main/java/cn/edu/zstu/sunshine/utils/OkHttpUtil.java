package cn.edu.zstu.sunshine.utils;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 对OkHttp网络框架的封装
 * Created by CooLoongWu on 2017-9-8 16:44.
 */

public class OkHttpUtil {

    private volatile static OkHttpUtil okHttpUtil;
    private OkHttpClient okHttpClient;
    private FormBody.Builder requestBody;
    private Request.Builder requestBuilder;
    private Request request;

    private OkHttpUtil() {
        this.okHttpClient = new OkHttpClient();
        requestBody = new FormBody.Builder();
        requestBuilder = new Request.Builder();
    }

    private OkHttpUtil(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        requestBuilder = new Request.Builder();
    }

    /**
     * @return OkHttp的单例
     */
    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }

    /**
     * @param okHttpClient 自定义的OkHttpClient
     * @return 自定义的OkHttp的单例
     */
    public static OkHttpUtil getInstance(OkHttpClient okHttpClient) {
        synchronized (OkHttpUtil.class) {
            if (okHttpUtil == null) {
                okHttpUtil = new OkHttpUtil(okHttpClient);
            }
        }
        return okHttpUtil;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 添加RequestBuilder请求地址
     *
     * @param url 地址
     * @return OkHttpUtil
     */
    public OkHttpUtil post(String url) {
        requestBuilder.url(url);
        return okHttpUtil;
    }

    /**
     * 添加RequestBody的参数
     *
     * @param key   参数键
     * @param value 参数值
     * @return OkHttpUtil
     */
    public OkHttpUtil addParam(String key, String value) {
        requestBody.add(key, value);
        return okHttpUtil;
    }

    /**
     * 构建RequestBuilder
     *
     * @return OkHttpUtil
     */
    public OkHttpUtil build() {
        request = requestBuilder.post(requestBody.build()).build();
        return okHttpUtil;
    }

    /**
     * @param callback 执行结果回调
     */
    public void enqueue(Callback callback) {
        okHttpClient.newCall(request).enqueue(callback);
    }
}
