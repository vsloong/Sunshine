package cn.edu.zstu.sunshine.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 对OkHttp网络框架的封装
 * Created by CooLoongWu on 2017-9-8 16:44.
 */

public class OkHttpUtil {

    private volatile static OkHttpUtil okHttpUtil;
    private static OkHttpClient okHttpClient;
    private static FormBody.Builder requestBody;
    private static Request.Builder requestBuilder;

    private static Request request;

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient();
        requestBody = new FormBody.Builder();
        requestBuilder = new Request.Builder();
    }

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

    public static OkHttpUtil.OkBuilder getBuilder() {
        getInstance();
        return new OkBuilder(requestBody, requestBuilder);
    }

    /**
     * @param callback 执行结果回调
     */
    public void enqueue(Callback callback) {
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 取消一个请求
     *
     * @param tag Call的标签
     */
    public void cancel(Context tag) {
        Dispatcher dispatcher = okHttpClient.dispatcher();
        synchronized (this) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                    Logger.e("删除队列中的某个请求");
                }
            }

            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                    Logger.e("删除正在执行的的某个请求");
                }
            }
        }
    }

    /**
     * 使用Builder模式构造参数
     */
    public static class OkBuilder {
        private FormBody.Builder requestBody;
        private Request.Builder requestBuilder;
        private boolean isPost = false;

        OkBuilder(FormBody.Builder requestBody, Request.Builder requestBuilder) {
            this.requestBody = requestBody;
            this.requestBuilder = requestBuilder;
        }

        public OkBuilder post() {
            isPost = true;
            return this;
        }

        public OkBuilder get() {
            isPost = false;
            return this;
        }

        public OkBuilder url(String url) {
            requestBuilder.url(url);
            return this;
        }

        public OkBuilder tag(Context tag) {
            requestBuilder.tag(tag);
            return this;
        }

        public OkBuilder addParam(String key, String value) {
            requestBody.add(key, value);
            return this;
        }

        public OkHttpUtil build() {
            if (isPost) {
                request = requestBuilder.post(requestBody.build()).build();
            } else {
                request = requestBuilder.get().build();
            }
            return okHttpUtil;
        }
    }
}
