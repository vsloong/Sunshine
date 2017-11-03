package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * ViewModel的基类
 * Created by CooLoongWu on 2017-11-2 15:39.
 *
 * @param <En> T是实体类
 */

public abstract class BaseViewModel<En> {

    protected Context context;
    protected ViewDataBinding binding;

    protected List<En> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    protected BaseViewModel(Context context, ViewDataBinding binding) {
        this.context = context;
        this.binding = binding;
        init();
    }

    public List<En> getData() {
        return data;
    }

    protected void setData(List<En> data) {
        this.data.clear();
        this.data.addAll(data);
        showEmptyView.set(data.size() <= 0);
    }

    public void init() {
        setData(loadDataFromLocal());
        loadDataIntoView();
    }

    protected void handelFailure() {
        Logger.e("获取信息失败");
    }

    protected void handelResponse(Response response) throws IOException {
        String data = response.body().string();
        Logger.e("获取信息成功：" + data);
        Logger.e("泛型");

//        final JsonParse<List<t>> jsonParse = JSON.parseObject(
//                data,
//                new TypeReference<JsonParse<List<t>>>() {
//                }
//        );
//
//        if (jsonParse.getCode() == 404) {
//            Logger.e("获取信息404");
//        } else {
//            DaoUtil.insertOrUpdate(jsonParse.getData());
//        }
    }

    protected abstract List<En> loadDataFromLocal();

    protected abstract void loadDataFromNetWork();

    protected abstract void loadDataIntoView();
}
