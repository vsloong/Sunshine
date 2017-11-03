package cn.edu.zstu.sunshine.tools.library;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import cn.edu.zstu.sunshine.base.Api;
import cn.edu.zstu.sunshine.base.AppConfig;
import cn.edu.zstu.sunshine.base.BaseViewModel;
import cn.edu.zstu.sunshine.databinding.ActivityLibraryBinding;
import cn.edu.zstu.sunshine.entity.BookBorrow;
import cn.edu.zstu.sunshine.entity.JsonParse;
import cn.edu.zstu.sunshine.greendao.BookBorrowDao;
import cn.edu.zstu.sunshine.utils.DaoUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    protected void loadDataFromNetWork() {
        Api.getLibraryInfo(context, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handelFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Logger.e("获取信息成功：" + data);

                final JsonParse<List<BookBorrow>> jsonParse = JSON.parseObject(
                        data,
                        new TypeReference<JsonParse<List<BookBorrow>>>() {
                        }
                );

                if (jsonParse.getCode() == 404) {
                    Logger.e("获取信息404");
                } else {
                    DaoUtil.insertOrUpdate(jsonParse.getData());
                }
            }
        });
    }
}
