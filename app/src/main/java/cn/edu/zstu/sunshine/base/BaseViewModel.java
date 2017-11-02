package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel的基类
 * Created by CooLoongWu on 2017-11-2 15:39.
 *
 * @param <T> T是实体类
 */

public abstract class BaseViewModel<T> {

    protected Context context;

    protected List<T> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    protected BaseViewModel(Context context) {
        this.context = context;
    }

    public List<T> getData() {
        return data;
    }

    protected void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public void init() {
        loadDataFromLocal();
        loadDataIntoView();
    }

    protected abstract void loadDataFromLocal();

    protected abstract void loadDataIntoView();
}
