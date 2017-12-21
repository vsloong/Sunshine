package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ViewModel的基类
 * Created by CooLoongWu on 2017-11-2 15:39.
 *
 * @param <T> T是实体类
 */

public abstract class BaseViewModel<T> {

    protected Context context;
    protected ViewDataBinding binding;

    protected List<T> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    protected BaseViewModel(Context context, ViewDataBinding binding) {
        this.context = context;
        this.binding = binding;
        init();
    }

    public List<T> getData() {
        return data;
    }

    protected void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        showEmptyView.set(data.size() <= 0);
    }

    public void init() {
        setData(loadDataFromLocal());
        loadDataIntoView();
    }

    public void loadDataFromNetWork() {
        String url = loadUrl();
        if (null == url || url.isEmpty()) return;
        Api.post(context, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handelFailure(Api.ERR_CODE_400);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handelResponse(response.body().string());
            }
        });
    }

    private void handelFailure(int errCode) {
        Logger.e("获取信息失败");
    }

    private void handelResponse(String jsonStr) {
        JsonParse<List<T>> s = parseStrToJson(jsonStr);
        if (s.getCode() == 404) {
            Logger.e("获取信息404");
            handelFailure(Api.ERR_CODE_404);
        } else {
            DaoUtil.insertOrUpdate(s.getData());
        }
    }

    public String loadUrl(T t) {
        String[] pkgName = t.getClass().getName().split("[.]");
        String className = pkgName[pkgName.length - 1];
        Logger.e("泛型名称：" + className);
        return className.toLowerCase();
    }

    protected abstract JsonParse<List<T>> parseStrToJson(String data);

    protected abstract String loadUrl();

    protected abstract List<T> loadDataFromLocal();

    protected abstract void loadDataIntoView();
}
