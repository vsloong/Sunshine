package cn.edu.zstu.sunshine.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 对OkHttp网络框架的封装
 * Created by CooLoongWu on 2017-9-8 16:44.
 */

public class OkHttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void getTest(String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    private String postTest(String url, String json) throws IOException {

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        return response.body().toString();
    }
}
