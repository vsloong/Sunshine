package cn.edu.zstu.sunshine.base;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;

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
    protected ViewDataBinding binding;

    protected List<T> data = new ArrayList<>();
    public ObservableBoolean showEmptyView = new ObservableBoolean(true);

    protected BaseViewModel(Context context, ViewDataBinding binding) {
        this.context = context;
        this.binding = binding;
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
        loadDataFromLocal();
        loadDataIntoView();
    }

    protected abstract void loadDataFromLocal();

    protected abstract void loadDataIntoView();
}
