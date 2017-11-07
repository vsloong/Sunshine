package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityLibraryBinding;
import cn.edu.zstu.sunshine.entity.BookBorrow;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.greendao.BookBorrowDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;

/**
 * 图书馆的ViewModel
 * Created by CooLoongWu on 2017-9-20 16:22.
 */

public class LibraryViewModel extends BaseViewModel<BookBorrow> {

    LibraryViewModel(Context context, ViewDataBinding binding) {
        super(context, binding);
    }

    @Override
    protected List<BookBorrow> loadDataFromLocal() {
        return DaoUtil.getInstance().getSession()
                .getBookBorrowDao()
                .queryBuilder()
                .where(
                        BookBorrowDao.Properties.UserId.eq(AppConfig.getDefaultUserId())
                )
                .list();
    }

    @Override
    protected void loadDataIntoView() {
        ActivityLibraryBinding libraryBinding = (ActivityLibraryBinding) binding;
        if (libraryBinding.include.recyclerView.getAdapter() != null) {
            libraryBinding.include.recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected JsonParse<List<BookBorrow>> parseStrToJson(String data) {
        return JSON.parseObject(
                data,
                new TypeReference<JsonParse<List<BookBorrow>>>() {
                }
        );
    }

    @Override
    protected String loadUrl() {
        return Api.URL_LIBRARY;
    }
}
